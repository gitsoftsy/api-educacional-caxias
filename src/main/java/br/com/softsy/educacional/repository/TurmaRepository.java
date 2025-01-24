package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Query("select turma from Turma turma join turma.escola escola where escola.idEscola = :idEscola")
	Optional<List<Turma>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

	@Procedure(name = "PROC_LISTA_TURMA_ESCOLA_DISCIPLINA")
	List<Object[]> filtrarTurmaPorDisciplinaEEscola(@Param("P_ID_ESCOLA") Long idEscola,
			@Param("P_ID_DISCIPLINA") Long idDisciplina);

	@Procedure(name = "PROC_LISTAR_TURMAS_SECRETARIA")
	List<Object[]> listarTurmasSecretaria(@Param("P_ID_CONTA") Long idConta);

	@Procedure(name = "PROC_LISTAR_ALUNOS_TURMA")
	List<Object[]> listarAlunosTurma(@Param("P_ID_TURMA") Long idTurma);

	@Procedure(name = "PROC_FILTRAR_TURMA_DISCIPLINAS")
	List<Object[]> filtrarTurmaDisciplinas(@Param("P_ID_PROFESSOR") Long idProfessor, @Param("P_ANO") Integer ano,
			@Param("P_ID_PERIODO_LETIVO") Long idPeriodoLetivo, @Param("P_ID_ESCOLA") Long idEscola,
			@Param("P_ID_DISCIPLINA") Long idDisciplina, @Param("P_ID_TRURNO") Long idTurno,
			@Param("P_ID_CURSO") Long idCurso);

	@Procedure(name = "PROC_FILTRAR_AVALIACAO")
	List<Object[]> filtrarTurmaAvaliacao(@Param("P_ID_ESCOLA") Long idEscola,
			@Param("P_ID_PERIODO_LETIVO") Long idPeriodoLetivo, @Param("P_ID_TRURNO") Long idTurno,
			@Param("P_ID_DISCIPLINA") Long idDisciplin

	);

	@Procedure(name = "PROC_FILTRAR_DISCIPLINA")
	List<Object[]> filtrarTurmaDisciplina(@Param("P_ID_ESCOLA") Long idEscola,
			@Param("P_ID_PERIODO_LETIVO") Long idPeriodoLetivo, @Param("P_ID_TRURNO") Long idTurno);

	@Procedure(name = "PROC_FILTRAR_TURNO")
	List<Object[]> filtrarTurmaTurno(@Param("P_ID_ESCOLA") Long idEscola,
			@Param("P_ID_PERIODO_LETIVO") Long idPeriodoLetivo

	);

	@Procedure(name = "PROC_LISTA_TURNO_PERIODO_LETIVO")
	List<Object[]> listarTurnosPorPeriodoLetivo(@Param("P_ID_PERIODO_LETIVO") Long idPeriodoLetivo);

}
