package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Municipio;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MunicipioDTO {

	private Long idMunicipio;

	@NotNull
	private String codIbge;

	private String nomeMunicipio;

	@NotNull
	private Long ufId;

	public MunicipioDTO(Municipio municipio) {
		this.idMunicipio = municipio.getIdMunicipio();
		this.codIbge = municipio.getCodIbge();
		this.nomeMunicipio = municipio.getNomeMunicipio();
		this.ufId = municipio.getUf().getIdUf();
	}

}
