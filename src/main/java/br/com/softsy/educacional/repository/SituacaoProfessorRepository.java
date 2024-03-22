package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.SituacaoProfessor;

public interface SituacaoProfessorRepository extends JpaRepository<SituacaoProfessor, Long>{
	
	List<SituacaoProfessor> findBySituacaoProfessor(String situacaoProfessor);

}
