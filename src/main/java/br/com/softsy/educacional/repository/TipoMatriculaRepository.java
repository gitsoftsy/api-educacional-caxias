package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.TipoMatricula;

public interface TipoMatriculaRepository extends JpaRepository<TipoMatricula, Long> {

	List<TipoMatricula> findByTipoMatricula(String tipoMatricula);
	
	@Query("select tipoMatricula from TipoMatricula tipoMatricula join tipoMatricula.conta conta where conta.idConta = :idConta")
    Optional<List<TipoMatricula>> findByConta_IdConta(@Param("idConta") Long idConta);
}
