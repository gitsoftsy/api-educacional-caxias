package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.DestinacaoLixo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DestinacaoLixoDTO {

	private Long idDestinacaoLixo;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private String destinacaoLixo;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public DestinacaoLixoDTO(DestinacaoLixo destinacaoLixo) {
		this.idDestinacaoLixo = destinacaoLixo.getIdDestinacaoLixo();
		this.destinacaoLixo = destinacaoLixo.getDestinacaoLixo();
		this.dataCadastro = destinacaoLixo.getDataCadastro();
		this.ativo = destinacaoLixo.getAtivo();
		this.dependenciaAdmId = destinacaoLixo.getDependenciaAdm().getIdDependenciaAdministrativa();
	}
}
