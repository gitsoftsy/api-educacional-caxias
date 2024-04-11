package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoDeMedicao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoDeMedicaoDTO {
	
	private Long idTipoMedicao;
	
	@NotNull
	private Long dependenciaAdmId;

	@NotNull
	private String tipoMedicao;

	private LocalDateTime dataCadastro;


	public TipoDeMedicaoDTO (TipoDeMedicao tipoMedicao) {
	        this.idTipoMedicao = tipoMedicao.getIdTipoMedicao();
	        this.tipoMedicao = tipoMedicao.getTipoMedicao();
	        this.dataCadastro = tipoMedicao.getDataCadastro();
	        this.dependenciaAdmId = tipoMedicao.getDependenciaAdm().getIdDependenciaAdministrativa();
	}

}
