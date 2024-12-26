package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.PeriodoLetivo;

public interface PeriodoLetivoRepository extends JpaRepository<PeriodoLetivo, Long>{
	
	List<PeriodoLetivo> findByDescricao (String descricao);
	
	@Query("select periodoLetivo from PeriodoLetivo periodoLetivo join periodoLetivo.conta conta where conta.idConta = :idConta")
    Optional<List<PeriodoLetivo>> findByConta_IdConta(@Param("idConta") Long idConta);
	
	
}
