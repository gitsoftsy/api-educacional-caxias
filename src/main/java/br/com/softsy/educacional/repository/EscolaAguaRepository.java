package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaAgua;

@Repository
public interface EscolaAguaRepository extends JpaRepository<EscolaAgua, Long>{
	
	@Query("select escolaAgua from EscolaAgua escolaAgua join escolaAgua.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaAgua>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
