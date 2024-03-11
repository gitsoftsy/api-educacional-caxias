package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.FonteEnergiaEletrica;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FonteEnergiaEletricaDTO {

	private Long idFonteEnergiaEletrica;
	
	@NotNull
	private String fonteEnergiaEletrica;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public FonteEnergiaEletricaDTO(FonteEnergiaEletrica energiaEletrica) {
		this.idFonteEnergiaEletrica = energiaEletrica.getIdFonteEnergiaEletrica();
		this.fonteEnergiaEletrica = energiaEletrica.getFonteEnergiaEletrica();
		this.dataCadastro = energiaEletrica.getDataCadastro();
		this.ativo = energiaEletrica.getAtivo();
	}
}
