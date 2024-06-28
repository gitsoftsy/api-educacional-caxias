package br.com.softsy.educacional.security;

import br.com.softsy.educacional.dto.LoginDTO;
import br.com.softsy.educacional.dto.UsuarioLogadoDTO;

public interface Autenticador {
	UsuarioLogadoDTO autenticar(LoginDTO login);
}
