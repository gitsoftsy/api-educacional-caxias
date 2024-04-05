package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.ProfessorFormacao;
public interface ProfessorFormacaoRepository extends JpaRepository<ProfessorFormacao, Long>{

	@Query("select professorFormacao from ProfessorFormacao professorFormacao join professorFormacao.professor professor where professor.idProfessor = :idProfessor")
    Optional<List<ProfessorFormacao>> findByProfessor_IdProfessor(@Param("idProfessor") Long idProfessor);
}
