package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Concurso;
import br.com.softsy.educacional.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long>{

	@Query("select serie from Serie serie join serie.conta conta where conta.idConta = :idConta")
    Optional<List<Serie>> findByConta_IdConta(@Param("idConta") Long idConta);
	
}
