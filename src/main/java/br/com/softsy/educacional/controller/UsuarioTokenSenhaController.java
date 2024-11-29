package br.com.softsy.educacional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        UsuarioTokenSenhaDTO response = new UsuarioTokenSenhaDTO(tokenGerado);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/validar")
    public ResponseEntity<String> validarToken(@RequestParam(required = false) String email,
                                               @RequestParam(required = false) String cpf,
                                               @RequestParam String token) {
        
        // Verifica se pelo menos um dos parâmetros (email ou cpf) foi fornecido
        if (email == null && cpf == null) {
            return ResponseEntity.status(400).body("Email ou CPF devem ser fornecidos.");
        }

        boolean isValid = usuarioTokenSenhaService.validarToken(email, cpf, token);

        if (isValid) {
            return ResponseEntity.ok("Token válido.");
        } else {
            return ResponseEntity.status(400).body("Token inválido ou expirado.");
        }
    }

    
    
    @DeleteMapping("/{idAvisoInterno}")
    public ResponseEntity<Void> excluir(@PathVariable Long idAvisoInterno) {
    	usuarioTokenSenhaService.excluir(idAvisoInterno);
        return ResponseEntity.ok().build();
    }
}
