package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Prematricula;

public interface PrematriculaRepository extends JpaRepository<Prematricula, Long> {

	@Query("select prematricula from Prematricula prematricula join prematricula.conta conta where conta.idConta = :idConta")
	Optional<List<Prematricula>> findByConta_IdConta(@Param("idConta") Long idConta);

	@Query("select prematricula from Prematricula prematricula join prematricula.turma turma where turma.idTurma = :idTurma")
	Optional<List<Prematricula>> findByTurma_IdTurma(@Param("idTurma") Long idTurma);

	List<Prematricula> findByAtivo(Character ativo);

	List<Prematricula> findByAlunoIdAluno(Long idAluno);

	List<Prematricula> findByDisciplinaIdDisciplina(Long idDisciplina);

	List<Prematricula> findByTipoMatriculaIdTipoMatricula(Long idTipoMatricula);

	List<Prematricula> findByPeriodoLetivoIdPeriodoLetivo(Long idPeriodoLetivo);

	List<Prematricula> findByTurmaIdTurma(Long idTurma);
}
