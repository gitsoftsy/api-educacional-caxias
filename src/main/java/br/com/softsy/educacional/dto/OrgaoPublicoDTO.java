package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.OrgaoPublico;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrgaoPublicoDTO {
	
	private Long idOrgaoPublico;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private String sigla;
	
	@NotNull
	private String orgaoPublico;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public OrgaoPublicoDTO(OrgaoPublico orgaoPublico) {
		this.idOrgaoPublico = orgaoPublico.getIdOrgaoPublico();
		this.sigla = orgaoPublico.getSigla();
		this.orgaoPublico = orgaoPublico.getOrgaoPublico();
		this.dataCadastro = orgaoPublico.getDataCadastro();
		this.ativo = orgaoPublico.getAtivo();
		this.dependenciaAdmId = orgaoPublico.getDependenciaAdm().getIdDependenciaAdministrativa();
	}
}