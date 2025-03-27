package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.softsy.educacional.model.Aplicacao;

@Repository
public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {
	
	List<Aplicacao> findAllByOrderByIdAplicacaoAsc();
	
	Optional<Aplicacao> findByAplicacaoIgnoreCase(String aplicacao);
	
	Optional<Aplicacao> findByAplicacao(String aplicacao);
	

}