package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.LinguaEnsino;


public interface LinguaEnsinoRepository   extends JpaRepository<LinguaEnsino, Long>{

	List<LinguaEnsino> findByLinguaEnsino(String linguaEnsino);

}
