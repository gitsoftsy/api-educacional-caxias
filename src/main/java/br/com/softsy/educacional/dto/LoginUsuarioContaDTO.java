package br.com.softsy.educacional.dto;

import java.util.List;

public class LoginUsuarioContaDTO {
	
	private UsuarioLogadoDTO usuarioLogado;
	private List<UsuarioContaDTO> usuarioConta;
	
	
	public UsuarioLogadoDTO getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(UsuarioLogadoDTO usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	public List<UsuarioContaDTO> getUsuarioConta() {
		return usuarioConta;
	}
	public void setUsuarioConta(List<UsuarioContaDTO> usuarioConta) {
		this.usuarioConta = usuarioConta;
	}
	
	
	

}
