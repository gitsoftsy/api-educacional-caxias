package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Zoneamento;

public interface ZoneamentoRepository extends JpaRepository<Zoneamento, Long>{
	
	List<Zoneamento> findByZoneamento(String zoneamento);
	
	@Query("select zoneamento from Zoneamento zoneamento join zoneamento.conta conta where conta.idConta = :idConta")
    Optional<List<Zoneamento>> findByConta_IdConta(@Param("idConta") Long idConta);

}
