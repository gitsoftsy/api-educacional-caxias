package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.TipoIngresso;

public interface TipoIngressoRepository extends JpaRepository<TipoIngresso, Long>{
	
	@Query("select tipoIngresso from TipoIngresso tipoIngresso join tipoIngresso.conta conta where conta.idConta = :idConta")
    Optional<List<TipoIngresso>> findByConta_IdConta(@Param("idConta") Long idConta);

}
