package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Zoneamento;

public interface ZoneamentoRepository extends JpaRepository<Zoneamento, Long>{
	
	List<Zoneamento> findByZoneamento(String zoneamento);

}
