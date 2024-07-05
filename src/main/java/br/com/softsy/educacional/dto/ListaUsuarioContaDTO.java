package br.com.softsy.educacional.dto;

import java.util.List;

public class ListaUsuarioContaDTO {

	private UsuarioDTO usuario;
	private List<UsuarioContaDTO> usuarioConta;
	
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	public List<UsuarioContaDTO> getUsuarioConta() {
		return usuarioConta;
	}
	public void setUsuarioConta(List<UsuarioContaDTO> usuarioConta) {
		this.usuarioConta = usuarioConta;
	}

	
	
	
}
