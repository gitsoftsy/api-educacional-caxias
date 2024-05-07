package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.TipoDependencia;

public interface TipoDependenciaRepository   extends JpaRepository<TipoDependencia, Long>{

	List<TipoDependencia> findByTipoDependencia(String tipoDependencia);
	
	@Query("select tipoDependencia from TipoDependencia tipoDependencia join tipoDependencia.conta conta where conta.idConta = :idConta")
    Optional<List<TipoDependencia>> findByConta_IdConta(@Param("idConta") Long idConta);
}
