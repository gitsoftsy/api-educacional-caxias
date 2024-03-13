package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoTelefone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoTelefoneDTO {
	
	private Long idTipoTelefone;
	
	@NotNull
	private String tipoTelefone;
	
	private LocalDateTime dataCadastro;
	
	public TipoTelefoneDTO(TipoTelefone tipoTelefone) {
		this.tipoTelefone= tipoTelefone.getTipoTelefone();
		this.idTipoTelefone = tipoTelefone.getIdTipoTelefone();
		this.dataCadastro = tipoTelefone.getDataCadastro();
	}


}
