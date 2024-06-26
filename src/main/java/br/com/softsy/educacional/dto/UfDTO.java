package br.com.softsy.educacional.dto;


import br.com.softsy.educacional.model.Uf;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UfDTO {
	
	private Long idUf;

	private String codUf;

	private String nomeUf;
	
	private String codigoIbge;
	
	public UfDTO(Uf uf) {
		this.idUf = uf.getIdUf();
		this.codUf = uf.getCodUf();
		this.nomeUf = uf.getNomeUf();
		this.codigoIbge = uf.getCodigoIbge();
	}

}
