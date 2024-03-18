package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.OrgaoPublico;

public interface OrgaoPublicoRepository extends JpaRepository<OrgaoPublico, Long>{

	List<OrgaoPublico> findByOrgaoPublico (String orgaoPublico);
	List<OrgaoPublico> findBySigla (String sigla);
}
