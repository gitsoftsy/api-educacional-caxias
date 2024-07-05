package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.model.UsuarioConta;

@Repository
public interface UsuarioContaRepository extends JpaRepository<UsuarioConta, Long>{

	@Query("select usuarioConta from UsuarioConta usuarioConta join usuarioConta.usuario usuario where usuario.idUsuario = :idUsuario")
    Optional<List<UsuarioConta>> findByUsuario_IdUsuario(@Param("idUsuario") Long idUsuario);
	
    @Modifying
    @Transactional
    @Query("delete from UsuarioConta usuarioConta where usuarioConta.usuario.idUsuario = :idUsuario")
    void deleteByUsuario_IdUsuario(@Param("idUsuario") Long idUsuario);
	
}
