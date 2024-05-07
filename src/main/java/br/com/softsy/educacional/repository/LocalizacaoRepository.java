package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Localizacao;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>{
	
	List<Localizacao> findByLocalizacao (String localizacao);
	
	@Query("select localizacao from Localizacao localizacao join localizacao.conta conta where conta.idConta = :idConta")
    Optional<List<Localizacao>> findByConta_IdConta(@Param("idConta") Long idConta);

}
