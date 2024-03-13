package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaTermoColaboracao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaTermoColaboracaoDTO {
	
	private Long idEscolaTermoColaboracao;
	
	private LocalDateTime dataCadastro;
	private LocalDateTime dataValidade;
	
	@NotNull
	private String coordenador;
	
	@NotNull
	private String termoColaboracao;
	
	@NotNull
	private Long escolaId;
	
	public CadastroEscolaTermoColaboracaoDTO(EscolaTermoColaboracao escolaTermoColaboracao) {
		this.idEscolaTermoColaboracao = escolaTermoColaboracao.getIdEscolaTermoColaboracao();
		this.dataCadastro = escolaTermoColaboracao.getDataCadastro();
		this.dataValidade = escolaTermoColaboracao.getDataValidade();
		this.coordenador = escolaTermoColaboracao.getCoordenador();
		this.termoColaboracao = escolaTermoColaboracao.getTermoColaboracao();
		this.escolaId = escolaTermoColaboracao.getEscola().getIdEscola();
	}

}
