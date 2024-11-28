package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

//    public String enviarEmail(String destinatario, String assunto, String corpo) throws MessagingException {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom(new InternetAddress("importante@alunos.sumare.edu.br"));
//            helper.setTo(destinatario);
//            helper.setSubject(assunto);
//            helper.setText(corpo);
//
//            javaMailSender.send(message);
//
//        } catch (MessagingException e) {
//            System.out.println("Erro ao criar o MimeMessage: " + e.getMessage());
//            throw e;
//        }
//
//        return "E-mail enviado com sucesso";
//    }
}
