package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.TurmaProfessor;

public interface TurmaProfessorRepository extends JpaRepository<TurmaProfessor, Long>{

	@Query("SELECT tp FROM TurmaProfessor tp JOIN tp.turma t JOIN tp.professor p WHERE p.idProfessor = :idProfessor")
	Optional<List<TurmaProfessor>> findByProfessor_IdProfessor(@Param("idProfessor") Long idProfessor);
	
}
