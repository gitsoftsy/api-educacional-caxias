package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    @Query("select matricula from Matricula matricula join matricula.conta conta where conta.idConta = :idConta")
    Optional<List<Matricula>> findByConta_IdConta(@Param("idConta") Long idConta);

    Optional<List<Matricula>> findByAlunoAluno(String aluno);

    List<Matricula> findByAlunoIdAluno(Long idAluno);

    List<Matricula> findByTipoMatriculaIdTipoMatricula(Long idTipoMatricula);

    List<Matricula> findByPeriodoLetivoIdPeriodoLetivo(Long idPeriodoLetivo);

    List<Matricula> findByTurmaIdTurma(Long idTurma);
}
