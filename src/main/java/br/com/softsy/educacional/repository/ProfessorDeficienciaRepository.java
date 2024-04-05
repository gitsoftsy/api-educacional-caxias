package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.ProfessorDeficiencia;

public interface ProfessorDeficienciaRepository extends JpaRepository<ProfessorDeficiencia, Long>{

	@Query("select professorDeficiencia from ProfessorDeficiencia professorDeficiencia join professorDeficiencia.professor professor where professor.idProfessor = :idProfessor")
    Optional<List<ProfessorDeficiencia>> findByProfessorDeficiencia_IdProfessor(@Param("idProfessor") Long idProfessor);
	
}
