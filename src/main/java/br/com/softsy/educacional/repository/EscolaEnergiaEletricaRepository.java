package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaEnergiaEletrica;

@Repository
public interface EscolaEnergiaEletricaRepository extends JpaRepository<EscolaEnergiaEletrica, Long>{

	@Query("select escolaEnergiaEletrica from EscolaEnergiaEletrica escolaEnergiaEletrica join escolaEnergiaEletrica.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaEnergiaEletrica>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
