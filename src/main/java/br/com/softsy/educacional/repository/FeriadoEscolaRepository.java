package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.FeriadoEscola;

@Repository
public interface FeriadoEscolaRepository extends JpaRepository<FeriadoEscola, Long>{

	@Query("select feriadoEscola from FeriadoEscola feriadoEscola join feriadoEscola.escola escola where escola.idEscola = :idEscola")
    Optional<List<FeriadoEscola>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
