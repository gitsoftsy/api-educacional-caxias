package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoAtoRegulatorio;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoAtoRegulatorioDTO {

	private Long idTipoAtoRegulatorio;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private String tipoAtoRegulatorio;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public TipoAtoRegulatorioDTO (TipoAtoRegulatorio tipoAtoRegulatorio) {
		this.idTipoAtoRegulatorio = tipoAtoRegulatorio.getIdTipoAtoRegulatorio();
		this.tipoAtoRegulatorio = tipoAtoRegulatorio.getTipoAtoRegulatorio();
		this.dataCadastro = tipoAtoRegulatorio.getDataCadastro();
		this.ativo = tipoAtoRegulatorio.getAtivo();
		this.dependenciaAdmId = tipoAtoRegulatorio.getDependenciaAdm().getIdDependenciaAdministrativa();
	}
}
