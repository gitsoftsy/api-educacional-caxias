package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.softsy.educacional.model.Aplicacao;

@Repository
public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {
	List<Aplicacao> findAllByOrderByIdAplicacaoAsc();
}