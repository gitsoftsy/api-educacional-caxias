package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.ProfessorEscola;

public interface ProfessorEscolaRepository extends JpaRepository<ProfessorEscola, Long>{

	@Query("select professorEscola from ProfessorEscola professorEscola join professorEscola.professor professor where professor.idProfessor = :idProfessor")
    Optional<List<ProfessorEscola>> findByProfessor_IdProfessor(@Param("idProfessor") Long idProfessor);
	
}
