package br.com.softsy.educacional.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.softsy.educacional.model.PeriodoLetivo;

public interface PeriodoLetivoRepository extends JpaRepository<PeriodoLetivo, Long>{
	
	List<PeriodoLetivo> findByDescricao (String descricao);
}
