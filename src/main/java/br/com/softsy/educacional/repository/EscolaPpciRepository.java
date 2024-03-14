package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaPpci;

@Repository
public interface EscolaPpciRepository extends JpaRepository<EscolaPpci, Long> {

	@Query("select escolaPpci from EscolaPpci escolaPpci join escolaPpci.escola escola where escola.idEscola = :idEscola")
	Optional<List<EscolaPpci>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
