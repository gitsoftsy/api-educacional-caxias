package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.Aplicacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AplicacaoDTO {

	private Long idAplicacao;

	private String aplicacao;

	public AplicacaoDTO(Aplicacao aplicacao) {
		this.idAplicacao = aplicacao.getIdAplicacao();
		this.aplicacao = aplicacao.getAplicacao();
	}
}
