package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.CandidatoRelacionamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroCandidatoRelacionamentoDTO {

	 private Long idCandidatoRelacionamento;
	 private Long candidatoId;
	 private Long pessoaId;
	 private Long papelPessoaId;
	 private Character ativo;
	 private LocalDateTime dataCadastro;

	    public CadastroCandidatoRelacionamentoDTO(CandidatoRelacionamento candidatoRelacionamento) {
	    	
	    	this.idCandidatoRelacionamento = candidatoRelacionamento.getIdCandidatoRelacionamento();
	        this.candidatoId = candidatoRelacionamento.getCandidato().getIdCandidato();
	        this.pessoaId = candidatoRelacionamento.getPessoa().getIdPessoa();
	        this.papelPessoaId = candidatoRelacionamento.getPapelPessoa().getIdPapelPessoa();
	        this.ativo = candidatoRelacionamento.getAtivo();
	        this.dataCadastro = candidatoRelacionamento.getDataCadastro();
	    }
}
