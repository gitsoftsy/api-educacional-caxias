package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.CategoriaEscolaPrivada;

public interface CategoriaEscolaPrivadaRepository extends JpaRepository<CategoriaEscolaPrivada, Long>{
	
	List<CategoriaEscolaPrivada> findByCategoriaEscolaPrivada(String categoriaEscolaPrivada);
	
	@Query("select categoriaEcolaPrivada from CategoriaEcolaPrivada categoriaEcolaPrivada join categoriaEcolaPrivada.conta conta where conta.idConta = :idConta")
    Optional<List<CategoriaEscolaPrivada>> findByConta_IdConta(@Param("idConta") Long idConta);

}
