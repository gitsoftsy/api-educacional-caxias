package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaLinkInternet;

public interface EscolaLinkInternetRepository extends JpaRepository<EscolaLinkInternet, Long>{

	@Query("select escolaLinkInternet from EscolaLinkInternet escolaLinkInternet join escolaLinkInternet.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaLinkInternet>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
