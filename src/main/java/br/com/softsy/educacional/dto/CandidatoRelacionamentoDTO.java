package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.CandidatoRelacionamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidatoRelacionamentoDTO {
	
	private Long idCandidatoRelacionamento;
    private CandidatoDTO candidato;
    private Long pessoa;
    private PapelPessoaDTO papelPessoa;
    private Character ativo;
    private LocalDateTime dataCadastro;

    public CandidatoRelacionamentoDTO(CandidatoRelacionamento candidatoRelacionamento) {

    	this.idCandidatoRelacionamento = candidatoRelacionamento.getIdCandidatoRelacionamento();
    	this.candidato = new CandidatoDTO(candidatoRelacionamento.getCandidato());
    	this.pessoa = candidatoRelacionamento.getPessoa().getIdPessoa();
        this.papelPessoa = new PapelPessoaDTO(candidatoRelacionamento.getPapelPessoa());
        this.ativo = candidatoRelacionamento.getAtivo();
        this.dataCadastro = candidatoRelacionamento.getDataCadastro();


    }
}
