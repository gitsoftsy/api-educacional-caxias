package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.softsy.educacional.model.DependenciaAdministrativa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DependenciaAdministrativaDTO {

	private Long idDependenciaAdministrativa;
	
	@NotNull
	private String dependenciaAdministrativa;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public DependenciaAdministrativaDTO(DependenciaAdministrativa dependenciaAdm) {
		this.idDependenciaAdministrativa = dependenciaAdm.getIdDependenciaAdministrativa();
		this.dependenciaAdministrativa = dependenciaAdm.getDependenciaAdministrativa();
		this.ativo = dependenciaAdm.getAtivo();
		this.dataCadastro = dependenciaAdm.getDataCadastro();
	}
}
