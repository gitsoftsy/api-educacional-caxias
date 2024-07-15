package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
	
	@Query("select transacao from Transacao transacao join transacao.modulo modulo where modulo.idModulo = :idModulo")
    Optional<List<Transacao>> findByModulo_IdModulo(@Param("idModulo") Long idModulo);
	
	@org.springframework.data.jpa.repository.Query(value = "CALL PROC_LISTA_ACESSOS_USUARIOS(:pIdUsuario)", nativeQuery = true)
    List<Object[]> listaAcessosUsuarios(@Param("pIdUsuario") Long idUsuario);
    
	@org.springframework.data.jpa.repository.Query(value = "CALL PROC_OBTEM_ACESSO_USUARIO_TRANSACAO(:pIdUsuario, :pIdTransacao)", nativeQuery = true)
    List<Object[]> listaAcessosUsuariosTransacao(@Param("pIdUsuario") Long idUsuario, @Param("pIdTransacao") Long idTransacao);
	

}
