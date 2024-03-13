package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.softsy.educacional.model.SituacaoFuncionamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SituacaoFuncionamentoDTO {
	
	private Long idSituacaoFuncionamento;
	
	@NotNull
	private String situacaoFuncionamento;
	@JsonIgnore
	private LocalDateTime dataCadastro;
	@JsonIgnore
	private Character ativo;
	
	public SituacaoFuncionamentoDTO(SituacaoFuncionamento situacaoFuncionamento) {
		this.situacaoFuncionamento = situacaoFuncionamento.getSituacaoFuncionamento();
		this.idSituacaoFuncionamento = situacaoFuncionamento.getIdSituacaoFuncionamento();
		this.dataCadastro = situacaoFuncionamento.getDataCadastro();
		this.ativo = situacaoFuncionamento.getAtivo();
	}

}
