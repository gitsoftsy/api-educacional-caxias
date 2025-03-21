package br.com.softsy.educacional.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.softsy.educacional.dto.CadastroResponsavelDTO;
import br.com.softsy.educacional.dto.CandidatoRelacionamentoDTO;
import br.com.softsy.educacional.dto.PessoaDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.model.CandidatoRelacionamento;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.repository.CandidatoRelacionamentoRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.service.CandidatoRelacionamentoService;
import br.com.softsy.educacional.service.PessoaCandidatoRelacionamentoService;
import br.com.softsy.educacional.service.PessoaService;

@RestController
@RequestMapping("/responsavel")
public class ResponsavelController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private CandidatoRelacionamentoService candidatoRelacionamentoService;
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CandidatoRelacionamentoRepository candidatoRepository;
    
	@Autowired
	private PasswordEncrypt encrypt;
	
    @Autowired
    private PessoaCandidatoRelacionamentoService pessoaCandidatoRelacionamentoService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	 @PostMapping("/pessoa-candidatoRelacionamento")
	    public ResponseEntity<Object> cadastrarPessoaECandidato(@RequestBody CadastroResponsavelDTO dto) {
	        try {
	        	
	        	PessoaDTO pessoaCPF = pessoaService.buscarPorCpfEIdConta(dto.getPessoaDTO().getCpf(),
						dto.getPessoaDTO().getContaId());
	        	
	        	if (pessoaCPF != null && pessoaCPF.getIdPessoa() != null) {
					dto.getCandidatoRelacionamentoDTO().setPessoaId(pessoaCPF.getIdPessoa());
				} else {
					Pessoa pessoa = pessoaService.criarPessoaAPartirDTO(dto.getPessoaDTO());
					pessoa.setSenha(encrypt.hashPassword(pessoa.getSenha()));
					pessoa = pessoaRepository.save(pessoa);
					
					dto.getCandidatoRelacionamentoDTO().setPessoaId(pessoa.getIdPessoa());
				}
	        	

	            CandidatoRelacionamento candidato = candidatoRelacionamentoService.criarCandidatoRelacionamentoAPartirDTO(dto.getCandidatoRelacionamentoDTO());
	            candidato = candidatoRepository.save(candidato);

	            CadastroResponseDTO responseDTO = new CadastroResponseDTO(candidato.getPessoa().getIdPessoa(), candidato.getIdCandidatoRelacionamento());
	            return ResponseEntity.ok(responseDTO);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar: " + e.getMessage());
	        }
	    }
	 
	    @PutMapping("/pessoa-candidatoRelacionamento")
	    public ResponseEntity<Object> atualizarPessoaECandidato(@RequestBody CadastroResponsavelDTO dto) {
	        try {
	            PessoaDTO pessoaDTO = pessoaService.atualizar(dto.getPessoaDTO());
	            CandidatoRelacionamentoDTO candidatoRelacionamentoDTO = candidatoRelacionamentoService.atualizar(dto.getCandidatoRelacionamentoDTO());

	            CadastroResponseDTO responseDTO = new CadastroResponseDTO(pessoaDTO.getIdPessoa(), candidatoRelacionamentoDTO.getIdCandidatoRelacionamento());
	            return ResponseEntity.ok(responseDTO);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar: " + e.getMessage());
	        }
	    }
	 
	    @GetMapping("/{idCandidatoRelacionamento}")
	    public ResponseEntity<Map<String, Object>> buscarResponsavelPorId(
	            @PathVariable Long idCandidatoRelacionamento) {

	        List<Object[]> resultados = entityManager.createQuery(
	                "SELECT CR.idCandidatoRelacionamento, PP.papelPessoa, PP.idPapelPessoa, P " +
	                        "FROM CandidatoRelacionamento CR " +
	                        "JOIN CR.pessoa P " +
	                        "JOIN CR.papelPessoa PP " +
	                        "WHERE CR.idCandidatoRelacionamento= :idCandidatoRelacionamento", Object[].class)
	                .setParameter("idCandidatoRelacionamento", idCandidatoRelacionamento)
	                .getResultList();

	        if (resultados.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        }

	        Object[] resultado = resultados.get(0);
	        Map<String, Object> responsavelJson = new HashMap<>();
	        responsavelJson.put("idResponsavel", resultado[0]);
	        responsavelJson.put("papelPessoa", resultado[1]);
	        responsavelJson.put("idPapelPessoa", resultado[2]);
	        responsavelJson.put("pessoa", resultado[3]);

	        return ResponseEntity.ok(responsavelJson);
	    }
	    
	 
	    @GetMapping("/candidato/{idCandidato}")
	    public ResponseEntity<List<Map<String, Object>>> buscarResponsaveisPorIdCandidato(
	            @PathVariable Long idCandidato) {

	        List<Object[]> resultados = entityManager.createQuery(
	                "SELECT CR.idCandidatoRelacionamento, PP.papelPessoa, PP.idPapelPessoa, P " +
	                        "FROM CandidatoRelacionamento CR " +
	                        "JOIN CR.pessoa P " +
	                        "JOIN CR.papelPessoa PP " +
	                        "WHERE CR.candidato.id = :idCandidato", Object[].class)
	                .setParameter("idCandidato", idCandidato)
	                .getResultList();

	        if (resultados.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        }

	        List<Map<String, Object>> responsaveisJson = resultados.stream().map(resultado -> {
	            Map<String, Object> responsavelJson = new HashMap<>();
	            responsavelJson.put("idResponsavel", resultado[0]);
	            responsavelJson.put("papelPessoa", resultado[1]);
	            responsavelJson.put("idPapelPessoa", resultado[2]);
	            responsavelJson.put("pessoa", resultado[3]);
	            return responsavelJson;
	        }).collect(Collectors.toList());

	        return ResponseEntity.ok(responsaveisJson);
	    }
	    
	    @GetMapping("/pessoa/{idPessoa}")
	    public ResponseEntity<List<Map<String, Object>>> buscarResponsaveisPorIdPessoa(
	            @PathVariable Long idPessoa) {

	        List<Object[]> resultados = entityManager.createQuery(
	                "SELECT CR.idCandidatoRelacionamento, PP.papelPessoa, PP.idPapelPessoa, P " +
	                        "FROM CandidatoRelacionamento CR " +
	                        "JOIN CR.pessoa P " +
	                        "JOIN CR.papelPessoa PP " +
	                        "WHERE CR.pessoa.id = :idPessoa", Object[].class)
	                .setParameter("idPessoa", idPessoa)
	                .getResultList();

	        if (resultados.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        }

	        List<Map<String, Object>> responsaveisJson = resultados.stream().map(resultado -> {
	            Map<String, Object> responsavelJson = new HashMap<>();
	            responsavelJson.put("idResponsavel", resultado[0]);
	            responsavelJson.put("papelPessoa", resultado[1]);
	            responsavelJson.put("idPapelPessoa", resultado[2]);
	            responsavelJson.put("pessoa", resultado[3]);
	            return responsavelJson;
	        }).collect(Collectors.toList());

	        return ResponseEntity.ok(responsaveisJson);
	    }
	 
	 

	    private static class CadastroResponseDTO {
	        private Long idPessoa;
	        private Long idCandidato;

	        public CadastroResponseDTO(Long idPessoa, Long idCandidato) {
	            this.idPessoa = idPessoa;
	            this.idCandidato = idCandidato;
	        }

	        // Getters e Setters
	        public Long getIdPessoa() {
	            return idPessoa;
	        }

	        public void setIdPessoa(Long idPessoa) {
	            this.idPessoa = idPessoa;
	        }

	        public Long getIdCandidato() {
	            return idCandidato;
	        }

	        public void setIdCandidato(Long idCandidato) {
	            this.idCandidato = idCandidato;
	        }
	    }
	
}
