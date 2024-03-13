package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaTratamentoLixo;
import br.com.softsy.educacional.model.TratamentoLixo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaTratamentoLixoDTO {
	
	private Long idEscolaTratamentoLixo;
	private TratamentoLixoDTO tratamentoLixoId;
	private Long escolaId;
	
	public EscolaTratamentoLixoDTO(EscolaTratamentoLixo tratamentoLixo) {
		this.idEscolaTratamentoLixo = tratamentoLixo.getIdEscolaTratamentoLixo();
		this.tratamentoLixoId = new TratamentoLixoDTO(tratamentoLixo.getTratamentoLixo());
		this.escolaId = tratamentoLixo.getEscola().getIdEscola();
	}
}
