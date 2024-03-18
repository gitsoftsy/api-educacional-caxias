package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.CategoriaEscolaPrivada;

public interface CategoriaEscolaPrivadaRepository extends JpaRepository<CategoriaEscolaPrivada, Long>{
	
	List<CategoriaEscolaPrivada> findByCategoriaEscolaPrivada(String categoriaEscolaPrivada);

}
