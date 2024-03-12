package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Localizacao;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>{
	
	List<Localizacao> findByLocalizacao (String localizacao);

}
