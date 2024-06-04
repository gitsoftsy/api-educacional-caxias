package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.ContaPadraoAcesso;

public interface AreaConhecimentoRepository extends JpaRepository<AreaConhecimento, Long>{
	
	List<AreaConhecimento> findByAreaConhecimento(String areaConhecimento);
	
	@Query("select areaConhecimento from AreaConhecimento areaConhecimento join areaConhecimento.conta conta where conta.idConta = :idConta")
    Optional<List<AreaConhecimento>> findByConta_IdConta(@Param("idConta") Long idConta);

}
