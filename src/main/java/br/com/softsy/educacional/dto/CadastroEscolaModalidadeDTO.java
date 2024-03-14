package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaModalidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaModalidadeDTO {

	
	private Long idEscolaModalidade;
	@NotNull
	private Long modalidadeEscolaId;
	@NotNull
	private Long escolaId;
	
	public CadastroEscolaModalidadeDTO(EscolaModalidade escolaModalidade) {
		this.idEscolaModalidade = escolaModalidade.getIdEscolaModalidade();
		this.modalidadeEscolaId = escolaModalidade.getModalidadeEscola().getIdModalidadeEscola();
		this.escolaId = escolaModalidade.getEscola().getIdEscola();
	}
}
