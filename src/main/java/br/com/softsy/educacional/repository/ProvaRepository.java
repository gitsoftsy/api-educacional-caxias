package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Prova;

public interface ProvaRepository extends JpaRepository<Prova, Long>{
	
	  @Query("select prova from Prova prova join prova.turma turma where turma.idTurma = :idTurma")
	  Optional<List<Prova>> findByTurma_IdTurma(@Param("idTurma") Long idTurma);
	  
	  @Procedure(name = "PROC_LISTAR_PROVAS")
	  List<Object[]> listarProvas(
	          @Param("P_ID_ESCOLA") Long idEscola,
	          @Param("P_ANO") Integer ano,
	          @Param("P_ID_PERIODO_LETIVO") Long idPeriodoLetivo,
	          @Param("P_ID_TURNO") Long idTurno,
	          @Param("P_ID_TURMA") Long idTurma,
	          @Param("P_ID_DISCIPLINA") Long idDisciplina
	  );

}
