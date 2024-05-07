package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.ProvedorInternet;
import br.com.softsy.educacional.model.Zoneamento;

public interface ProvedorInternetRepository extends JpaRepository<ProvedorInternet, Long>{
	
	List<ProvedorInternet> findByProvedorInternet(String provedorInternet);
	
	@Query("select provedorInternet from ProvedorInternet provedorInternet join provedorInternet.conta conta where conta.idConta = :idConta")
    Optional<List<ProvedorInternet>> findByConta_IdConta(@Param("idConta") Long idConta);

}
