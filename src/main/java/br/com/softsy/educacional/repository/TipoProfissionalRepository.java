package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.TipoProfissional;
public interface TipoProfissionalRepository extends JpaRepository<TipoProfissional, Long>{
	
	List<TipoProfissional> findByTipoProfissional (String tipoProfissional);
	
	@Query("select tipoProfissional from TipoProfissional tipoProfissional join tipoProfissional.conta conta where conta.idConta = :idConta")
    Optional<List<TipoProfissional>> findByConta_IdConta(@Param("idConta") Long idConta);

}
