package br.com.softsy.educacional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.softsy.educacional.dto.CadastroResponsavelDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.CandidatoRelacionamento;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.repository.CandidatoRelacionamentoRepository;
import br.com.softsy.educacional.repository.CandidatoRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.service.CandidatoRelacionamentoService;
import br.com.softsy.educacional.service.CandidatoService;
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
	
	 @PostMapping("/pessoa-candidato")
	    public ResponseEntity<Object> cadastrarPessoaECandidato(@RequestBody CadastroResponsavelDTO dto) {
	        try {
	            Pessoa pessoa = pessoaService.criarPessoaAPartirDTO(dto.getPessoaDTO());
	            pessoa.setSenha(encrypt.hashPassword(pessoa.getSenha()));
	            pessoa = pessoaRepository.save(pessoa);

	            dto.getCandidatoRelacionamentoDTO().setPessoaId(pessoa.getIdPessoa());

	            CandidatoRelacionamento candidato = candidatoRelacionamentoService.criarCandidatoRelacionamentoAPartirDTO(dto.getCandidatoRelacionamentoDTO());
	            candidato = candidatoRepository.save(candidato);

	            CadastroResponseDTO responseDTO = new CadastroResponseDTO(pessoa.getIdPessoa(), candidato.getIdCandidatoRelacionamento());
	            return ResponseEntity.ok(responseDTO);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar: " + e.getMessage());
	        }
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
