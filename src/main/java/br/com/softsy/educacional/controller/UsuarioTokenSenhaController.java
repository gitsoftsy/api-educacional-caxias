package br.com.softsy.educacional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.softsy.educacional.dto.UsuarioTokenSenhaDTO;
import br.com.softsy.educacional.model.UsuarioTokenSenha;
import br.com.softsy.educacional.service.UsuarioTokenSenhaService;

@RestController
@RequestMapping("/usuarioToken")
public class UsuarioTokenSenhaController {

    @Autowired
    private UsuarioTokenSenhaService usuarioTokenSenhaService;

    @Autowired
    private JavaMailSender emailSender;
    
    
    @PostMapping("/{usuarioId}")
    public ResponseEntity<UsuarioTokenSenhaDTO> gerarToken(@PathVariable Long usuarioId) {
        UsuarioTokenSenha tokenGerado = usuarioTokenSenhaService.gerarToken(usuarioId);

        // Retorna o token gerado encapsulado no DTO
        UsuarioTokenSenhaDTO response = new UsuarioTokenSenhaDTO(tokenGerado);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/enviar-token-email/{email}/{aluno}")
//    public ResponseEntity<String> enviarTokenPorEmail(@PathVariable String email, @PathVariable String aluno) {
//        if (!isValidEmail(email)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de endereço de e-mail inválido");
//        }
//
//        String token = usuarioTokenSenhaService.gerarNumeroAleatorio().toString();
//
//        String mensagem = enviarTokenPorEmailAluno(email, token);
//
//        if (mensagem.equals("E-mail enviado com sucesso")) {
//            return ResponseEntity.ok(mensagem);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagem);
//        }
//    }
//
//    public String enviarTokenPorEmailAluno(String email, String token) {
//        try {
//            MimeMessage message = emailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setFrom(new InternetAddress("importante@alunos.sumare.edu.br"));
//            helper.setTo(email);
//            helper.setSubject("Recebemos sua solicitação de nova senha");
//            helper.setText("<!DOCTYPE html>" +
//                    "<html lang='pt-br'>" +
//                    "<body style='font-family: Arial, sans-serif; color: #333;'>" +
//                    "<h2>Prezado(a) aluno(a),</h2>" +
//                    "<p>Recebemos sua solicitação para redefinição de senha. Use o código abaixo:</p>" +
//                    "<h1 style='color: #0084ff; text-align: center;'>" + token + "</h1>" +
//                    "<p>Se você não solicitou a alteração de senha, desconsidere esta mensagem.</p>" +
//                    "<p>Atenciosamente,<br>Equipe Sumaré.</p>" +
//                    "</body>" +
//                    "</html>", true);
//
//            emailSender.send(message);
//            return "E-mail enviado com sucesso";
//        } catch (MessagingException e) {
//            return "Erro ao enviar e-mail";
//        }
//    }
//
//    private boolean isValidEmail(String email) {
//        return email.matches("^\\S+@\\S+\\.\\S+$");
//    }
}
