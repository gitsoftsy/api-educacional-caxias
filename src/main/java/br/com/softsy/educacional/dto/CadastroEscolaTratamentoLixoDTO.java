package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaTratamentoLixo;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CadastroEscolaTratamentoLixoDTO {
	private Long idEscolaTratamentoLixo;
	private Long tratamentoLixoId;
	private Long escolaId;
	
	public CadastroEscolaTratamentoLixoDTO(EscolaTratamentoLixo tratamentoLixo) {
		this.idEscolaTratamentoLixo = tratamentoLixo.getIdEscolaTratamentoLixo();
		this.tratamentoLixoId = tratamentoLixo.getTratamentoLixo().getIdTratamentoLixo();
		this.escolaId = tratamentoLixo.getEscola().getIdEscola();
	}
}
