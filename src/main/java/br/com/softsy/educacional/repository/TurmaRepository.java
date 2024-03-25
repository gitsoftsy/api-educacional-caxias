package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long>{
	
	@Query("select turma from Turma turma join turma.escola escola where escola.idEscola = :idEscola")
    Optional<List<Turma>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
