package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaProfissional;

public interface EscolaProfissionalRepository extends JpaRepository<EscolaProfissional, Long>{

	@Query("select escolaProfissional from EscolaProfissional escolaProfissional join escolaProfissional.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaProfissional>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
