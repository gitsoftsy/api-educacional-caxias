package br.com.softsy.educacional.dto;

import java.time.LocalDate;

import br.com.softsy.educacional.model.CandidatoRelacionamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidatoRelacionamentoDTO {
	
	private Long idCandidatoRelacionamento;
    private CandidatoDTO candidato;
    private PessoaDTO pessoa;
    private PapelPessoaDTO papelPessoa;
    private Character ativo;
    private LocalDate dataCadastro;

    public CandidatoRelacionamentoDTO(CandidatoRelacionamento candidatoRelacionamento) {
//
//    	this.idCandidatoRelacionamento = ca
//    	this.candidato = new CandidatoDTO(candidatoRelacionamento.getCandidato());
//        this.pessoa = new PessoaDTO(candidatoRelacionamento.getPessoa());
//        this.papelPessoa = new PapelPessoaDTO(candidatoRelacionamento.getPapaelPessoa());
//        this.ativo = candidatoRelacionamento.getAtivo();
//        this.dataCadastro = candidatoRelacionamento.getDataCadastro();

}
}
