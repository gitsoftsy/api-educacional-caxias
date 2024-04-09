package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.FornecimentoAgua;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FornecimentoAguaDTO {

	private Long idFornecimentoAgua;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private String fornecimentoAgua;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public FornecimentoAguaDTO(FornecimentoAgua fornecimento) {
		this.idFornecimentoAgua = fornecimento.getIdFornecimentoAgua();
		this.fornecimentoAgua = fornecimento.getFornecimentoAgua();
		this.dataCadastro = fornecimento.getDataCadastro();
		this.ativo = fornecimento.getAtivo();
		this.dependenciaAdmId = fornecimento.getDependenciaAdm().getIdDependenciaAdministrativa();
	}
	
}
