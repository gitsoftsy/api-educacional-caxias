package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.FormaOcupacaoPredio;

public interface FormaOcupacaoPredioRepository extends JpaRepository<FormaOcupacaoPredio, Long>{

	List<FormaOcupacaoPredio> findByFormaOcupacaoPredio(String formaOcupacaoPredio);
	
	@Query("select formaOcupacaoPredio from FormaOcupacaoPredio formaOcupacaoPredio join formaOcupacaoPredio.conta conta where conta.idConta = :idConta")
    Optional<List<FormaOcupacaoPredio>> findByConta_IdConta(@Param("idConta") Long idConta);
}
