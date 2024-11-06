package br.com.softsy.educacional.security;

import br.com.softsy.educacional.dto.LoginDTO;
import br.com.softsy.educacional.dto.UsuarioLogadoDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.UsuarioRepository;

public class UsuarioAutenticador implements Autenticador {

	private UsuarioRepository usuarioRepository;
	private PasswordEncrypt encrypt;

	public UsuarioAutenticador(UsuarioRepository usuarioRepository, PasswordEncrypt encrypt) {
		this.usuarioRepository = usuarioRepository;
		this.encrypt = encrypt;
	}

	@Override
	public UsuarioLogadoDTO autenticar(LoginDTO login) {
		Usuario usuario = usuarioRepository.findUsuarioByUsuarioAndAtivo(login.getUsuario(), 'S');

		if (usuario == null) {
			throw new UsuarioDesativadoException("Esse usuário está desativado.");
		} else {
			if (usuario != null && encrypt.checkPass(login.getSenha(), usuario.getSenha())) {
				return new UsuarioLogadoDTO(usuario.getIdUsuario(), usuario.getDataCadastro(), usuario.getUsuario(),
						usuario.getNomeCompleto(), usuario.getEmail(), usuario.getEmailVerificado(), usuario.getCpf(),
						usuario.getDataNascimento(), usuario.getSenha(), usuario.getAtivo(), usuario.getCelular(),
						usuario.getCelularVerificado());

			}
		}

		return null;

	}

	public class UsuarioDesativadoException extends RuntimeException {
		public UsuarioDesativadoException(String message) {
			super(message);
		}
	}
}
