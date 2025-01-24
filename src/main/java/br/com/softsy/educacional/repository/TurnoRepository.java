package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long>{
	
	List<Turno> findByTurno(String turno);
	List<Turno> findByMnemonico(String mnemonico);
	
	@Query("select turno from Turno turno join turno.conta conta where conta.idConta = :idConta")
    Optional<List<Turno>> findByConta_idConta(@Param("idConta") Long idConta);
	

	Optional<List<Turno>> findActiveTurnoByConta_IdContaAndAtivo(Long idConta, Character ativo);
	
	@Procedure(name = "PROC_LSA_TURNO_POR_PERIODO_SERIE_DISCIPLINA_ESCOLA")
		List<Object[]> listarTurnoPorPeriodoSerieDisciplinaEscola(
				@Param("P_ID_PERIODO_LETIVO") Long idPeriodoLetivo,
				@Param("P_ID_ESCOLA ") Long idEscola,
				@Param("P_ID_DISCIPLINA ") Long idDisciplina,
				@Param("P_ID_SERIE  ") Long idSerie);
	
}
