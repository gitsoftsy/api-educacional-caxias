package br.com.softsy.educacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.UsuarioTokenSenha;

public interface UsuarioTokenSenhaRepository  extends JpaRepository<UsuarioTokenSenha, Long>{

}
