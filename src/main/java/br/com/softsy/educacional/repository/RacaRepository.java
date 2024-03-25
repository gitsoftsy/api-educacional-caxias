package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Raca;

public interface RacaRepository extends JpaRepository<Raca, Long>{
	
	List<Raca> findByRaca (String raca);

}
