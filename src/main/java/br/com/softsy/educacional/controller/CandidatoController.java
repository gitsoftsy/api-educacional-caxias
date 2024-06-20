package br.com.softsy.educacional.controller;

import java.net.URI;
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

import br.com.softsy.educacional.dto.CadastroCandidatoDTO;
import br.com.softsy.educacional.dto.CadastroCandidatoPessoaDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.dto.StepCandidatoDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
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

    
    private static class CadastroResponseDTO {
        private String candidato;
        private Long idCandidato;

        public CadastroResponseDTO(String candidato, Long idCandidato) {
            this.candidato = candidato;
            this.idCandidato = idCandidato;
        }

        // Getters e Setters
        public String getCandidato() {
            return candidato;
        }

        public void setCandidato(String candidato) {
            this.candidato = candidato;
        }

        public Long getIdCandidato() {
            return idCandidato;
        }

        public void setIdCandidato(Long idCandidato) {
            this.idCandidato = idCandidato;
        }
    }
    
	 @PostMapping("/pessoa-candidato")
	    public ResponseEntity<Object> cadastrarPessoaECandidato(@RequestBody CadastroCandidatoPessoaDTO dto) {
	        try {
	            Pessoa pessoa = pessoaService.criarPessoaAPartirDTO(dto.getPessoaDTO());
	            pessoa.setSenha(encrypt.hashPassword(pessoa.getSenha()));
	            pessoa = pessoaRepository.save(pessoa);

	            dto.getCandidatoDTO().setPessoaId(pessoa.getIdPessoa());

	            Candidato candidato = candidatoService.criarCandidatoAPartirDTO(dto.getCandidatoDTO());
	            candidato = candidatoRepository.save(candidato);

	            CadastroResponseDTO responseDTO = new CadastroResponseDTO(candidato.getCandidato(), candidato.getIdCandidato());
	            return ResponseEntity.ok(responseDTO);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar: " + e.getMessage());
	        }
	    }
    
    
    @GetMapping
    public ResponseEntity<List<CandidatoDTO>> listar() {
        List<CandidatoDTO> candidatos = candidatoService.listarTudo();
        return ResponseEntity.ok(candidatos);
    }

    @GetMapping("/{idCandidato}")
    public ResponseEntity<CandidatoDTO> buscarPorId(@PathVariable Long idCandidato) {
        CandidatoDTO candidatoDto = candidatoService.buscarPorId(idCandidato);
        return ResponseEntity.ok(candidatoDto);
    }
    
    @PutMapping("/candidato/{idCandidato}/oferta/{idOfertaConcurso}")
    public ResponseEntity<Map<String, Object>> updateCandidatoOfertaConcurso(
            @PathVariable Long idCandidato,
            @PathVariable Long idOfertaConcurso
        ) {
            Map<String, Object> response = candidatoService.updateCandidatoOfertaConcurso(idCandidato, idOfertaConcurso);
            return ResponseEntity.ok(response);
        }

    @PostMapping
    public ResponseEntity<CandidatoDTO> cadastrar(@RequestBody @Valid CadastroCandidatoDTO dto) {
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
    public Object obtemStepCandidato(
            @RequestParam(value = "idCandidato", required = false) Long idCandidato,
            @RequestParam(value = "candidato", required = false) String candidato,
            @RequestParam(value = "rgNum", required = false) String rgNum,
            @RequestParam(value = "cpfNum", required = false) String cpfNum,
            @RequestParam(value = "certNasc", required = false) String certNasc,
            @RequestParam(value = "certCasamento", required = false) String certCasamento
    ) {
        // Verifica se todos os parâmetros são nulos
        if (idCandidato == null && candidato == null && rgNum == null && cpfNum == null && certNasc == null && certCasamento == null) {
            return "Por favor, informe ao menos um parâmetro na requisição.";
        }

        List<Map<String, Object>> result = candidatoService.obtemStepCandidato(idCandidato, candidato, rgNum, cpfNum, certNasc, certCasamento);

        if (result.isEmpty()) {
            return "Nenhum resultado encontrado para os parâmetros informados.";
        }

        return result;
    }
    
}
