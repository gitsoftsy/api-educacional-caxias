package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaDestinacaoLixoDTO {

	private Long idEscolaDestinacaoLixo;
	@NotNull
	private Long escolaId;
	@NotNull
	private Long destinacaoLixoId;
	
	public CadastroEscolaDestinacaoLixoDTO(EscolaDestinacaoLixo escolaDestinacaoLixo) {
		this.idEscolaDestinacaoLixo = escolaDestinacaoLixo.getIdEscolaDestinacaoLixo();
		this.escolaId = escolaDestinacaoLixo.getEscola().getIdEscola();
		this.destinacaoLixoId = escolaDestinacaoLixo.getDestinacaoLixo().getIdDestinacaoLixo();
	}
}
