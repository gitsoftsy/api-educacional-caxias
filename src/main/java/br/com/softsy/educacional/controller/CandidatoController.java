package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.AtualizarResponseDTO;
import br.com.softsy.educacional.dto.CadastroCandidatoDTO;
import br.com.softsy.educacional.dto.CadastroCandidatoPessoaDTO;
import br.com.softsy.educacional.dto.CadastroResponseDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.dto.PessoaDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.repository.CandidatoRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.service.CandidatoService;
import br.com.softsy.educacional.service.PessoaService;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private CandidatoService candidatoService;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private CandidatoRepository candidatoRepository;

	@Autowired
	private PasswordEncrypt encrypt;

	@PersistenceContext
	private EntityManager entityManager;

	@PostMapping("/pessoa-candidato")
	public ResponseEntity<Object> cadastrarPessoaECandidato(@RequestBody CadastroCandidatoPessoaDTO dto) {
		try {
			PessoaDTO pessoaCPF = null;

			if (dto.getPessoaDTO().getCpf() != null) {
				pessoaCPF = pessoaService.buscarPorCpfEIdConta(dto.getPessoaDTO().getCpf(),
						dto.getPessoaDTO().getContaId());
			}

			if (pessoaCPF != null && pessoaCPF.getIdPessoa() != null) {
				dto.getCandidatoDTO().setPessoaId(pessoaCPF.getIdPessoa());
			} else {

				Pessoa pessoa = pessoaService.criarPessoaAPartirDTO(dto.getPessoaDTO());
				pessoa.setSenha(encrypt.hashPassword(pessoa.getSenha()));
				pessoa = pessoaRepository.save(pessoa);

				dto.getCandidatoDTO().setPessoaId(pessoa.getIdPessoa());
			}

			Candidato candidato = candidatoService.criarCandidatoAPartirDTO(dto.getCandidatoDTO());
			candidato = candidatoRepository.save(candidato);

			CadastroResponseDTO responseDTO = new CadastroResponseDTO(candidato.getCandidato(),
					candidato.getIdCandidato());
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar: " + e.getMessage());
		}
	}

	@PutMapping("/pessoa-candidato")
	public ResponseEntity<Object> atualizarPessoaECandidato(@RequestBody CadastroCandidatoPessoaDTO dto) {
		try {
			PessoaDTO pessoaDTO = pessoaService.atualizar(dto.getPessoaDTO());
			CandidatoDTO candidatoDTOAtualizado = candidatoService.atualizar(dto.getCandidatoDTO());

			AtualizarResponseDTO responseDTO = new AtualizarResponseDTO(pessoaDTO.getIdPessoa(),
					candidatoDTOAtualizado.getIdCandidato());
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar: " + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<CandidatoDTO>> listar() {
		List<CandidatoDTO> candidatos = candidatoService.listarTudo();
		return ResponseEntity.ok(candidatos);
	}

	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<CandidatoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
		List<CandidatoDTO> curso = candidatoService.buscarPorIdConta(idConta);
		return ResponseEntity.ok(curso);
	}

	@GetMapping("/{idCandidato}")
	public ResponseEntity<CandidatoDTO> buscarPorId(@PathVariable Long idCandidato) {
		CandidatoDTO candidatoDto = candidatoService.buscarPorId(idCandidato);
		return ResponseEntity.ok(candidatoDto);
	}

	@PutMapping("/candidato/{idCandidato}/oferta/{idOfertaConcurso}")
	public ResponseEntity<Map<String, Object>> updateCandidatoOfertaConcurso(@PathVariable Long idCandidato,
			@PathVariable Long idOfertaConcurso) {
		Map<String, Object> response = candidatoService.updateCandidatoOfertaConcurso(idCandidato, idOfertaConcurso);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastroCandidatoDTO dto) {
		CandidatoDTO candidatoDTO = candidatoService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(candidatoDTO.getIdCandidato()).toUri();
		return ResponseEntity.created(uri).body(candidatoDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroCandidatoDTO dto) {
		return ResponseEntity.ok(candidatoService.atualizar(dto));
	}

	@DeleteMapping("/{idCandidato}")
	public ResponseEntity<?> excluir(@PathVariable Long idCandidato) {
		candidatoService.remover(idCandidato);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/step")
	public Object obtemStepCandidato(@RequestParam(value = "idCandidato", required = false) Long idCandidato,
			@RequestParam(value = "candidato", required = false) String candidato,
			@RequestParam(value = "rgNum", required = false) String rgNum,
			@RequestParam(value = "cpfNum", required = false) String cpfNum,
			@RequestParam(value = "certNasc", required = false) String certNasc,
			@RequestParam(value = "certCasamento", required = false) String certCasamento) {

		if (idCandidato == null && candidato == null && rgNum == null && cpfNum == null && certNasc == null
				&& certCasamento == null) {
			return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
		}

		List<Map<String, Object>> result = candidatoService.obtemStepCandidato(idCandidato, candidato, rgNum, cpfNum,
				certNasc, certCasamento);

		if (result.isEmpty()) {
			return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
		}

		return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
	}

	@GetMapping("/reservaFinal")
	public Object listaDadosReservaFinal(@RequestParam(value = "candidato", required = false) String candidato) {
		if (candidato == null) {
			return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
		}

		List<Map<String, Object>> result = candidatoService.obtemStepCandidato(candidato);

		if (result.isEmpty()) {
			return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
		}

		return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
	}

	@GetMapping("/listaReservaDeVagas")
	public Object obtemListaReservaDeVagas(@RequestParam(value = "idUsuario", required = false) Long idUsuario) {
		if (idUsuario == null) {
			return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
		}

		List<Map<String, Object>> result = candidatoService.obtemListaReservaDeVagas(idUsuario);

		if (result.isEmpty()) {
			return ResponseEntity.ok(
					new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
		}

		return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
	}
	
	@GetMapping("/listarReservasPorDocumento")
	public Object obtemListaReservaDeVagasPorDocumento(@RequestParam(value = "idConta") Long idConta,
			@RequestParam(value = "idEscola", required = false) Long idEscola,
			@RequestParam(value = "rgNum", required = false) String rgNum,
			@RequestParam(value = "cpfNum", required = false) String cpfNum,
			@RequestParam(value = "certNasc", required = false) String certNasc,
			@RequestParam(value = "certCasamento", required = false) String certCasamento) {
		if (idConta == null && idEscola == null && rgNum == null && cpfNum == null && certNasc == null
				&& certCasamento == null) {
			return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
		}

		if (idConta == null) {
			return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
		}

		List<Map<String, Object>> result = candidatoService.obtemListaReservaDeVagasPorDoc(idConta, idEscola, rgNum,
				cpfNum, certNasc, certCasamento);

		if (result.isEmpty()) {
			return ResponseEntity.ok(
					new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
		}

		return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
	}

	@GetMapping("/excel/{idConta}")
	public ResponseEntity<?> listarReservaDeVagasExcel(@PathVariable Long idConta) {
		List<Map<String, Object>> result = candidatoService.listarReservaDeVagasExcel(idConta);
		if (result.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Nenhuma reserva encontrada para a conta informada.");
		}
		return ResponseEntity.ok(result);
	}

	@PutMapping("/{idCandidato}/aprovar")
	public ResponseEntity<?> ativar(@PathVariable Long idCandidato) {
	    if (idCandidato == null) {
	        return ResponseEntity.badRequest().body("O ID do candidato não pode ser nulo.");
	    }
	    
	    System.out.println("ID do Candidato recebido: " + idCandidato);
	    
	    candidatoService.aprovaReprova('S', idCandidato);
	    return ResponseEntity.ok().build();
	}


	@PutMapping("/{idCandidato}/reprovar")
	public ResponseEntity<?> reprovarCandidato(@PathVariable Long idCandidato,
			@RequestBody CadastroCandidatoDTO candidatoDTO) {

		candidatoService.reprovarCandidato(idCandidato, candidatoDTO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Candidato reprovado com sucesso!");
		response.put("idCandidato", idCandidato);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/filtrar")
	public Object filtrarPainelDeVagas(@RequestParam(value = "idUsuario", required = false) Long idUsuario,
			@RequestParam(value = "idConcurso", required = false) Long idConcurso,
			@RequestParam(value = "idOfertaConcurso", required = false) Long idOfertaConcurso,
			@RequestParam(value = "idEscola", required = false) Long idEscola) {

		if (idUsuario == null && idConcurso == null) {

			return ResponseEntity.badRequest().body(
					new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
		}

		List<Map<String, Object>> result = candidatoService.filtrarReservasDeVagas(idUsuario, idConcurso,
				idOfertaConcurso, idEscola);

		if (result.isEmpty()) {
			return ResponseEntity.ok(
					new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
		}

		return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
	}

}
