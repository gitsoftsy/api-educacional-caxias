package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long>{
	
	List<Modulo> findByModulo(String modulo);
	
	@Query(value = "CALL PROC_OBTEM_ACESSO_USUARIO_MODULO(:pIdModulo)", nativeQuery = true)
    List<Object[]> listaAcessosUsuariosModulo(@Param("pIdModulo") Long idModulo);

}
