package br.com.softsy.educacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.GradeCurricular;

@Repository
public interface GradeCurricularRepository extends JpaRepository<GradeCurricular, Long>{

	
}
