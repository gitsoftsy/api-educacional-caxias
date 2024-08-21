package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.SituacaoAluno;

@Repository
public interface SituacaoAlunoRepository extends JpaRepository<SituacaoAluno, Long>{
	
	@Query("select situacaoAluno from SituacaoAluno situacaoAluno join situacaoAluno.conta conta where conta.idConta = :idConta")
    Optional<List<SituacaoAluno>> findByConta_IdConta(@Param("idConta") Long idConta);

}
