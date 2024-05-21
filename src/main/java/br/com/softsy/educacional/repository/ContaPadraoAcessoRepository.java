package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.ContaPadraoAcesso;

@Repository
public interface ContaPadraoAcessoRepository extends JpaRepository<ContaPadraoAcesso, Long>{
	
	@Query("select contaPadraoAcesso from ContaPadraoAcesso contaPadraoAcesso join contaPadraoAcesso.conta conta where conta.idConta = :idConta")
    Optional<List<ContaPadraoAcesso>> findByConta_IdConta(@Param("idConta") Long idConta);
	
}
