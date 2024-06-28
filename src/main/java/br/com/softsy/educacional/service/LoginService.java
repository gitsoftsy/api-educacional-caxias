package br.com.softsy.educacional.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softsy.educacional.dto.LoginDTO;
import br.com.softsy.educacional.dto.LoginUsuarioContaDTO;
import br.com.softsy.educacional.dto.UsuarioContaDTO;
import br.com.softsy.educacional.dto.UsuarioLogadoDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.security.Autenticador;
import br.com.softsy.educacional.security.UsuarioAutenticador;

@Service
public class LoginService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioContaService usuarioContaService;
	
	@Autowired
	private PasswordEncrypt encrypt;
	
	public UsuarioLogadoDTO autenticar(LoginDTO login) {
		Autenticador autenticador = null;
		
		autenticador = new UsuarioAutenticador(repository, encrypt);
		
		return autenticador.autenticar(login);
	}
	
    public LoginUsuarioContaDTO autenticarEObterContas(LoginDTO login) {
        UsuarioLogadoDTO usuarioLogado = autenticar(login);
        if (usuarioLogado == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        List<UsuarioContaDTO> usuarioContas = usuarioContaService.buscarPorIdUsuario(usuarioLogado.getIdUsuario());

        LoginUsuarioContaDTO loginEBuscaDeUsuario = new LoginUsuarioContaDTO();
        loginEBuscaDeUsuario.setUsuarioLogado(usuarioLogado);
        loginEBuscaDeUsuario.setUsuarioConta(usuarioContas);

        return loginEBuscaDeUsuario;
    }

}
