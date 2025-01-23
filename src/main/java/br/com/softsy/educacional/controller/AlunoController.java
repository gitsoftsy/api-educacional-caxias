package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import br.com.softsy.educacional.dto.AlunoDTO;
import br.com.softsy.educacional.dto.CadastroAlunoDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
	private AlunoService alunoService;

	@GetMapping
	public ResponseEntity<List<AlunoDTO>> listar() {
		List<AlunoDTO> alunos = alunoService.listarTudo();
		return ResponseEntity.ok(alunos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Long id) {
		AlunoDTO alunoDTO = alunoService.buscarPorId(id);
		return ResponseEntity.ok(alunoDTO);
	}

	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<AlunoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
		List<AlunoDTO> alunos = alunoService.buscarPorIdConta(idConta);
		return ResponseEntity.ok(alunos);
	}

	@PostMapping
	public ResponseEntity<AlunoDTO> cadastrar(@RequestBody @Valid CadastroAlunoDTO dto) {
		AlunoDTO alunoDTO = alunoService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(alunoDTO.getIdAluno())
				.toUri();
		return ResponseEntity.created(uri).body(alunoDTO);
	}

	@PutMapping
	public ResponseEntity<AlunoDTO> atualizar(@RequestBody @Valid CadastroAlunoDTO dto) {
		return ResponseEntity.ok(alunoService.atualizar(dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		alunoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/semPrematricula")
	public ResponseEntity<AllResponse> listarAlunosSemPrematricula(
			@RequestParam(value = "idTurma", required = false) Long idTurma) {

		if (idTurma == null) {
			return ResponseEntity.badRequest()
					.body(new AllResponse("Por favor, informe o parâmetro na requisição.", new ArrayList<>()));
		}

		List<Map<String, Object>> result = alunoService.listarAlunosSemPrematricula(idTurma);

		if (result.isEmpty()) {
			return ResponseEntity
					.ok(new AllResponse("Não existem resultados para essa requisição.", new ArrayList<>()));
		}

		return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
	}

	@GetMapping("/turmaDisciplina")
	public Object listarTurmaDiscipliaAluno(@RequestParam(value = "idAluno", required = false) Long idAluno) {
		if (idAluno == null) {
			return "Por favor, informe o parâmetro na requisição.";
		}

		List<Map<String, Object>> result = alunoService.listarTurmaDisciplinaAluno(idAluno);

		if (result.isEmpty()) {
			return "Nenhum resultado encontrado para os parâmetros informados.";
		}

		return result;
	}

	@GetMapping("/{idConta}/filtrar")
	public ResponseEntity<AllResponse> filtrarAlunos(@PathVariable(value = "idConta") Long idConta,
			@RequestParam(value = "matricula", required = false) String matricula,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "idEscola", required = false) Long idEscola,
			@RequestParam(value = "idCurso", required = false) Long idCurso) {

		if (matricula == null && nome == null && cpf == null && idEscola == null && idCurso == null) {
			return ResponseEntity.badRequest().body(
					new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
		}

		if (nome != null && nome.trim().length() < 3) {
			return ResponseEntity.badRequest()
					.body(new AllResponse("O nome deve ter ao menos 3 caracteres para o filtro.", new ArrayList<>()));
		}

		if (cpf != null) {
			String cpfLimpo = cpf.replaceAll("[^\\d]", "");
			if (cpfLimpo.length() < 5) {
				return ResponseEntity.badRequest().body(
						new AllResponse("O CPF deve ter ao menos 5 caracteres para o filtro.", new ArrayList<>()));
			}
			cpf = cpfLimpo;
		}

		List<Map<String, Object>> result = alunoService.filtrarAlunos(matricula, nome, cpf, idEscola, idCurso);

		if (result.isEmpty()) {
			return ResponseEntity
					.ok(new AllResponse("Não existem resultados para essa requisição.", new ArrayList<>()));
		}

		return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
	}
	

	@GetMapping("/disciplinasDispPreMatr")
	public Object listarDisciplinasDisponiveisPreMatricula(
			@RequestParam(value = "idAluno", required = false) Long idAluno,
			@RequestParam(value = "idSerie", required = false) Long idSerie,
			@RequestParam(value = "idPeriodoLetivo", required = false) Long idPeriodoLetivo,
			@RequestParam(value = "idEscola", required = false) Long idEscola) {

		if (idAluno == null || idSerie == null || idPeriodoLetivo == null) {
			return "Por favor, informe todos os parâmetros obrigatórios na requisição: idAluno, idSerie e idPeriodoLetivo.";
		}

		List<Map<String, Object>> result = alunoService.listarDisciplinasDisponivesPrematrcula(idAluno, idSerie,
				idPeriodoLetivo, idEscola);

		if (result.isEmpty()) {
			return "Nenhum resultado encontrado para os parâmetros informados.";
		}

		return result;
	}

}