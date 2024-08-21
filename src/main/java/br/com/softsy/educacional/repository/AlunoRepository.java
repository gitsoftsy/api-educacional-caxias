package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.AreaConhecimento;
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{

	@Query("select aluno from Aluno aluno join aluno.conta conta where conta.idConta = :idConta")
    Optional<List<Aluno>> findByConta_IdConta(@Param("idConta") Long idConta);
	
}
