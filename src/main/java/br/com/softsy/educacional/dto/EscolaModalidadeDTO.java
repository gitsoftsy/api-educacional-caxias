package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaModalidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaModalidadeDTO {

	private Long idEscolaModalidade;
	private ModalidadeEscolaDTO modalidadeEscola;
	private Long escolaId;
	
	public EscolaModalidadeDTO(EscolaModalidade escolaModalidade) {
		this.idEscolaModalidade = escolaModalidade.getIdEscolaModalidade();
		this.modalidadeEscola = new ModalidadeEscolaDTO(escolaModalidade.getModalidadeEscola());
		this.escolaId = escolaModalidade.getEscola().getIdEscola();
	}
}
