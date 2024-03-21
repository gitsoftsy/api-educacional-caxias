package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.AnoEscolar;

public interface AnoEscolarRepository extends JpaRepository<AnoEscolar, Long>{
	
	List<AnoEscolar> findByAnoEscolar(String anoEscolar);

}
