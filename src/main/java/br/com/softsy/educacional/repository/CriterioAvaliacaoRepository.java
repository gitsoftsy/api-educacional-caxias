package br.com.softsy.educacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.CriterioAvaliacao;

@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, Long>{

}
