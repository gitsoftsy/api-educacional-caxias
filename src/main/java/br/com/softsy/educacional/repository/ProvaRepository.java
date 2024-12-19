package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Prova;

public interface ProvaRepository extends JpaRepository<Prova, Long>{
	
	  @Query("select prova from Prova prova join prova.turma turma where turma.idTurma = :idTurma")
	    Optional<List<Prova>> findByTurma_IdTurma(@Param("idTurma") Long idTurma);
	  


}
