package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaDispositivo;

@Repository
public interface EscolaDispositivoRepository extends JpaRepository<EscolaDispositivo, Long>{

	@Query("select escolaDispositivo from EscolaDispositivo escolaDispositivo join escolaDispositivo.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaDispositivo>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
