package br.com.softsy.educacional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.softsy.educacional.dto.LoginDTO;
import br.com.softsy.educacional.dto.LoginUsuarioContaDTO;
import br.com.softsy.educacional.security.UsuarioAutenticador.UsuarioDesativadoException;
import br.com.softsy.educacional.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@PostMapping
    public ResponseEntity<Object> autenticarEObterContas(@RequestBody LoginDTO login) {
        try {
        	LoginUsuarioContaDTO loginUsuario = loginService.autenticarEObterContas(login);
            return ResponseEntity.ok(loginUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("O usuário ou a senha são inválidos");
        } catch (UsuarioDesativadoException e) {
        	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        }
    }
	
	public class ErrorResponse {
		private String message;

		public ErrorResponse(String message) {
			this.message = message;
		}

		// Getter e Setter
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

}
