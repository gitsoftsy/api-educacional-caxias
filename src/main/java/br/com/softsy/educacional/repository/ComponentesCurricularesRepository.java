package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.ComponentesCurriculares;

public interface ComponentesCurricularesRepository extends JpaRepository<ComponentesCurriculares, Long> {
	
	List<ComponentesCurriculares> findByComponentesCurriculares(String componentesCurriculares);

}
