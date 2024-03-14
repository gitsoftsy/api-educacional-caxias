package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaDestinacaoLixoDTO {

	private Long idEscolaDestinacaoLixo;
	private Long escolaId;
	private DestinacaoLixoDTO destinacaoLixo;
	
	public EscolaDestinacaoLixoDTO(EscolaDestinacaoLixo escolaDestinacaoLixo) {
		this.idEscolaDestinacaoLixo = escolaDestinacaoLixo.getIdEscolaDestinacaoLixo();
		this.escolaId = escolaDestinacaoLixo.getEscola().getIdEscola();
		this.destinacaoLixo = new DestinacaoLixoDTO(escolaDestinacaoLixo.getDestinacaoLixo());
	}
}