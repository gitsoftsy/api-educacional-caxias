package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.CargoProfessor;

public interface CargoProfessorRepository extends JpaRepository<CargoProfessor, Long>{
	
	List<CargoProfessor> findByCargoProfessor(String cargoProfessor);

}
