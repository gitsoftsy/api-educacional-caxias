package br.com.softsy.educacional.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.UsuarioTokenSenha;

@Repository
public interface UsuarioTokenSenhaRepository  extends JpaRepository<UsuarioTokenSenha, Long>{

	 Optional<UsuarioTokenSenha> findByUsuarioEmailOrUsuarioCpfAndToken(String email, String cpf, String token);

}
