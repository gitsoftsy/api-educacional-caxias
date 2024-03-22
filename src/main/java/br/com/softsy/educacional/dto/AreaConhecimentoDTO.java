package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.AreaConhecimento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AreaConhecimentoDTO {
	
	private Long idAreaConhecimento;

	private String areaConhecimento;

	private LocalDateTime dataCadastro;
	
    public AreaConhecimentoDTO(AreaConhecimento areaConhecimento) {
        this.idAreaConhecimento = areaConhecimento.getIdAreaConhecimento();
        this.areaConhecimento = areaConhecimento.getAreaConhecimento();
        this.dataCadastro = areaConhecimento.getDataCadastro();
    }

}
