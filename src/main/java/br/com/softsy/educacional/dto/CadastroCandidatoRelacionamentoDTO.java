package br.com.softsy.educacional.dto;

import java.time.LocalDate;

import br.com.softsy.educacional.model.CandidatoRelacionamento;

public class CadastroCandidatoRelacionamentoDTO {

	 private Long idCandidatoRelacionamento;
	 private Long candidatoId;
	 private Long pessoaId;
	 private Long papelPessoaId;
	 private Character ativo;
	 private LocalDate dataCadastro;

	    public CadastroCandidatoRelacionamentoDTO(CandidatoRelacionamento candidatoRelacionamento) {
	    	this.idCandidatoRelacionamento = candidatoRelacionamento.getCandidatoRelacionamento();
	        this.candidatoId = candidatoRelacionamento.getCandidato().getIdCandidato();
	        this.pessoaId = candidatoRelacionamento.getPessoa().getIdPessoa();
	        this.papelPessoaId = candidatoRelacionamento.getPapaelPessoa().getIdPapelPessoa();
	        this.ativo = candidatoRelacionamento.getAtivo();
	        this.dataCadastro = candidatoRelacionamento.getDataCadastro();
	    }
}
