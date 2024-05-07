package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EntidadeSuperior;

public interface EntidadeSuperiorRepository extends JpaRepository<EntidadeSuperior, Long>{

	List<EntidadeSuperior> findByEntidadeSuperior (String entidadeSuperior);
	
	@Query("select entidadeSuperior from EntidadeSuperior entidadeSuperior join entidadeSuperior.conta conta where conta.idConta = :idConta")
    Optional<List<EntidadeSuperior>> findByConta_IdConta(@Param("idConta") Long idConta);
}
