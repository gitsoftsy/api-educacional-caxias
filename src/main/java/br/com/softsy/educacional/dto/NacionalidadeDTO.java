package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Nacionalidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NacionalidadeDTO {

	private Long idNacionalidade;

	private String nacionalidade;

	private LocalDateTime dataCadastro;

	private Character ativo;

	public NacionalidadeDTO(Nacionalidade nacionalidade) {
		this.idNacionalidade = nacionalidade.getIdNacionalidade();
		this.nacionalidade = nacionalidade.getNacionalidade();
		this.dataCadastro = nacionalidade.getDataCadastro();
		this.ativo = nacionalidade.getAtivo();
	}
}
