package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDTO {

	@NotBlank
	private String usuario;
	
	@NotBlank
	private String senha;
	
}
