package br.com.softsy.educacional.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.model.UsuarioTokenSenha;

public interface UsuarioTokenSenhaRepository  extends JpaRepository<UsuarioTokenSenha, Long>{

	 Optional<UsuarioTokenSenha> findByUsuarioEmailOrUsuarioCpfAndToken(String email, String cpf, String token);

}
