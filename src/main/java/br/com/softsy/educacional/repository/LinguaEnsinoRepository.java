package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.LinguaEnsino;
import br.com.softsy.educacional.model.Zoneamento;

@Repository
public interface LinguaEnsinoRepository   extends JpaRepository<LinguaEnsino, Long>{

	List<LinguaEnsino> findByLinguaEnsino(String linguaEnsino);
	
	@Query("select linguaEnsino from LinguaEnsino linguaEnsino join linguaEnsino.conta conta where conta.idConta = :idConta")
    Optional<List<LinguaEnsino>> findByConta_IdConta(@Param("idConta") Long idConta);

}
