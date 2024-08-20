package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.FeriadoConta;

@Repository
public interface FeriadoContaRepository extends JpaRepository<FeriadoConta, Long>{
	
	
	@Query("select feriadoConta from FeriadoConta feriadoConta join feriadoConta.conta conta where conta.idConta = :idConta")
    Optional<List<FeriadoConta>> findByConta_IdConta(@Param("idConta") Long idConta);

}
