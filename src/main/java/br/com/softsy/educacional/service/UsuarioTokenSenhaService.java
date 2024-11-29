package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.model.UsuarioTokenSenha;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.repository.UsuarioTokenSenhaRepository;

@Service
public class UsuarioTokenSenhaService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UsuarioTokenSenhaRepository usuarioTokenSenhaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioTokenSenha gerarToken(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        UsuarioTokenSenha usuarioTokenSenha = new UsuarioTokenSenha();

        usuarioTokenSenha.setUsuario(usuario);
        usuarioTokenSenha.setToken(gerarNumeroAleatorio());
        usuarioTokenSenha.setDataCadastro(LocalDateTime.now());
        usuarioTokenSenha.setDataValidade(LocalDateTime.now().plusMinutes(15)); // Token válido por 15 minutos
        usuarioTokenSenha.setAtivo('S');
        usuarioTokenSenha.setUtilizado('N');

        return usuarioTokenSenhaRepository.save(usuarioTokenSenha);
    }

    private String gerarNumeroAleatorio() {
        Random random = new Random();
        int numero = random.nextInt(999999); // Gera um número de 6 dígitos
        return String.format("%06d", numero); // Retorna no formato 000000
    }
    
    public boolean validarToken(String email, String cpf, String token) {
        // Valida se o e-mail ou o CPF foi informado (não os dois)
        if (email != null && cpf != null) {
            throw new IllegalArgumentException("Informe apenas um dos dois: Email ou CPF.");
        }

        Optional<UsuarioTokenSenha> usuarioTokenSenhaOpt;
        if (email != null) {
            usuarioTokenSenhaOpt = usuarioTokenSenhaRepository.findByUsuarioEmailOrUsuarioCpfAndToken(email, null, token);
        } else {
            usuarioTokenSenhaOpt = usuarioTokenSenhaRepository.findByUsuarioEmailOrUsuarioCpfAndToken(null, cpf, token);
        }

        // Verifica se o token é válido e não foi utilizado
        return usuarioTokenSenhaOpt.isPresent() &&
               usuarioTokenSenhaOpt.get().getDataValidade().isAfter(LocalDateTime.now()) &&
               usuarioTokenSenhaOpt.get().getUtilizado() == 'N';
    }

    
    @Transactional
    public void excluir(Long id) {
    	usuarioTokenSenhaRepository.deleteById(id);
    }


}
