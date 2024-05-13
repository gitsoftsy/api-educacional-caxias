package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;

import br.com.softsy.educacional.model.Escola;

public class ImagemEscolaDTO extends EscolaDTO{
	
	private Long idEscola;
	
	@NotNull
	private String logoEscola;
	
	public ImagemEscolaDTO(Escola imagemEscola) {
		this.idEscola = imagemEscola.getIdEscola();
		this.logoEscola = Base64.encodeBase64String(imagemEscola.getLogoEscola());
	}

}
