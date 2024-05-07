package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.DestinacaoLixo;

@Repository
public interface DestinacaoLixoRepository extends JpaRepository<DestinacaoLixo, Long>{

	List<DestinacaoLixo> findByDestinacaoLixo (String destinacaoLixo);
	
	@Query("select destinacaoLixo from DestinacaoLixo join destinacaoLixo.conta conta where conta.idConta = :idConta")
    Optional<List<DestinacaoLixo>> findByConta_IdConta(@Param("idConta") Long idConta);
}
