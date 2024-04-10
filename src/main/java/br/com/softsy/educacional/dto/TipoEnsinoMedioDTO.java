package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoEnsinoMedio;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoEnsinoMedioDTO {

	private Long idTipoEnsinoMedio;
	
	@NotNull
	private Long dependenciaAdmId;

	private String tipoEnsinoMedio;

	private LocalDateTime dataCadastro;

	private Character ativo;

	public TipoEnsinoMedioDTO(TipoEnsinoMedio tipoEnsinoMedioDTO) {
		this.idTipoEnsinoMedio = tipoEnsinoMedioDTO.getIdTipoEnsinoMedio();
		this.tipoEnsinoMedio = tipoEnsinoMedioDTO.getTipoEnsinoMedio();
		this.dataCadastro = tipoEnsinoMedioDTO.getDataCadastro();
		this.ativo = tipoEnsinoMedioDTO.getAtivo();
		this.dependenciaAdmId = tipoEnsinoMedioDTO.getDependenciaAdm().getIdDependenciaAdministrativa();
	}
}
