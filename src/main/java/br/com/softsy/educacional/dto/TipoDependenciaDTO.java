package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoDependencia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoDependenciaDTO {
	
	private Long idTipoDependencia;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private String tipoDependencia;
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public TipoDependenciaDTO(TipoDependencia tipoDependencia) {
		this.tipoDependencia = tipoDependencia.getTipoDependencia();
		this.idTipoDependencia = tipoDependencia.getIdTipoDependencia();
		this.dataCadastro = tipoDependencia.getDataCadastro();
		this.ativo = tipoDependencia.getAtivo();
		this.dependenciaAdmId = tipoDependencia.getDependenciaAdm().getIdDependenciaAdministrativa();
	}


}
