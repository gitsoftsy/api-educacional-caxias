package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

	@Query("select matricula from Matricula matricula join matricula.conta conta where conta.idConta = :idConta")
	Optional<List<Matricula>> findByConta_IdConta(@Param("idConta") Long idConta);

	@Query("select matricula from Matricula matricula join matricula.aluno aluno where aluno.aluno = :aluno")
	Optional<List<Matricula>> findByAluno_Aluno(@Param("aluno") String aluno);

	@Procedure(name = "PROC_DADOS_ACAD_MAT_ALUNO")
	List<Object[]> dadosMatriculaAluno(@Param("p_id_aluno") Long idAluno);

	List<Matricula> findByAlunoIdAluno(Long idAluno);

	List<Matricula> findByTipoMatriculaIdTipoMatricula(Long idTipoMatricula);

	List<Matricula> findByPeriodoLetivoIdPeriodoLetivo(Long idPeriodoLetivo);

	List<Matricula> findByTurmaIdTurma(Long idTurma);
}
