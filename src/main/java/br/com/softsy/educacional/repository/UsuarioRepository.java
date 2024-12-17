package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	List<Usuario> findByUsuario(String usuario);
	List<Usuario> findByEmail(String email);
	List<Usuario> findByCpf(String cpf);
	
    @Procedure(name = "PROC_LISTAR_USUARIOS_CONTA")
    List<Object[]> listarUsuariosIdConta(
            @Param("P_ID_CONTA") Long idConta
    );

	Usuario findUsuarioByUsuarioAndAtivo(String usuario, Character ativo);
	
}
