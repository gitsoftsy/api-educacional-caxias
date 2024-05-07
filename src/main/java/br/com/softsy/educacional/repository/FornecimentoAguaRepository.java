package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.FornecimentoAgua;
import br.com.softsy.educacional.model.Zoneamento;

@Repository
public interface FornecimentoAguaRepository extends JpaRepository<FornecimentoAgua, Long>{

	List<FornecimentoAgua> findByFornecimentoAgua(String fornecimentoAgua);
	
	@Query("select fornecimentoAgua from FornecimentoAgua fornecimentoAgua join fornecimentoAgua.conta conta where conta.idConta = :idConta")
    Optional<List<FornecimentoAgua>> findByConta_IdConta(@Param("idConta") Long idConta);
}
