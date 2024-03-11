package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.FormaOcupacaoPredio;

public interface FormaOcupacaoPredioRepository extends JpaRepository<FormaOcupacaoPredio, Long>{

	List<FormaOcupacaoPredio> findByFormaOcupacaoPredio(String formaOcupacaoPredio);
}
