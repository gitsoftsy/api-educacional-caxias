package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;

import br.com.softsy.educacional.model.EscolaTermoColaboracao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaTermoColaboracaoDTO {
	
	private Long idEscolaTermoColaboracao;
	
	private LocalDateTime dataCadastro;
	private LocalDateTime dataValidade;
	
	@NotNull
	private String coordenador;
	
	@NotNull
	private String termoColaboracao;
	
	private String anexo;
	
	@NotNull
	private Long escolaId;
	
	
	
	public EscolaTermoColaboracaoDTO(EscolaTermoColaboracao escolaTermoColaboracao) {
		this.idEscolaTermoColaboracao = escolaTermoColaboracao.getIdEscolaTermoColaboracao();
		this.dataCadastro = escolaTermoColaboracao.getDataCadastro();
		this.dataValidade = escolaTermoColaboracao.getDataValidade();
		this.coordenador = escolaTermoColaboracao.getCoordenador();
		this.termoColaboracao = escolaTermoColaboracao.getTermoColaboracao();
		this.anexo = Base64.encodeBase64String(escolaTermoColaboracao.getAnexo());
		this.escolaId = escolaTermoColaboracao.getEscola().getIdEscola();
	}	

}
