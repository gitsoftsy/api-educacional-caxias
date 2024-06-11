package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.PapelPessoa;

@Repository
public interface PapelPessoaRepository extends JpaRepository<PapelPessoa, Long>{

	@Query("select papelPessoa from PapelPessoa papelPessoa join papelPessoa.conta conta where conta.idConta = :idConta")
    Optional<List<PapelPessoa>> findByConta_IdConta(@Param("idConta") Long idConta);
	
}
