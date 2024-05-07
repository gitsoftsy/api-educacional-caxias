package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.OrgaoPublico;

public interface OrgaoPublicoRepository extends JpaRepository<OrgaoPublico, Long>{

	List<OrgaoPublico> findByOrgaoPublico (String orgaoPublico);
	List<OrgaoPublico> findBySigla (String sigla);
	
	@Query("select orgaoPublico from OrgaoPublico orgaoPublico join orgaoPublico.conta conta where conta.idConta = :idConta")
    Optional<List<OrgaoPublico>> findByConta_IdConta(@Param("idConta") Long idConta);
}
