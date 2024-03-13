package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Escola;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long>{

	List<Escola> findByNomeEscola(String nomeEscola);
	List<Escola> findEscolaByAtivo(Character ativo);
}
