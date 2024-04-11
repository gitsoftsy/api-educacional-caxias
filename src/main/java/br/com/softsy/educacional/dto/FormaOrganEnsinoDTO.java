package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.FormaOrganEnsino;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormaOrganEnsinoDTO {

	private Long idFormaOrganEnsino;
	
	@NotNull
	private Long dependenciaAdmId;

	private String formaOrganEnsino;

	private LocalDateTime dataCadastro;

	public FormaOrganEnsinoDTO(FormaOrganEnsino formaOrganEnsino) {
		this.idFormaOrganEnsino = formaOrganEnsino.getIdFormaOrganEnsino();
		this.formaOrganEnsino = formaOrganEnsino.getFormaOrganEnsino();
		this.dataCadastro = formaOrganEnsino.getDataCadastro();
		this.dependenciaAdmId = formaOrganEnsino.getDependenciaAdm().getIdDependenciaAdministrativa();

	}
}
