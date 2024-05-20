package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaEsgoto;

public interface EscolaEsgotoRepository extends JpaRepository<EscolaEsgoto, Long>{

	@Query("select escolaEsgoto from EscolaEsgoto escolaEsgoto join escolaEsgoto.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaEsgoto>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
