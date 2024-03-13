package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.softsy.educacional.model.Localizacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocalizacaoDTO {

	private Long idLocalizacao;
	
	@NotNull
	private String localizacao;
	
	@JsonIgnore
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	private Character ativo;
	
	public LocalizacaoDTO(Localizacao localizacao) {
		this.idLocalizacao = localizacao.getIdLocalizacao();
		this.localizacao = localizacao.getLocalizacao();
		this.dataCadastro = localizacao.getDataCadastro();
		this.ativo = localizacao.getAtivo();
	}
}
