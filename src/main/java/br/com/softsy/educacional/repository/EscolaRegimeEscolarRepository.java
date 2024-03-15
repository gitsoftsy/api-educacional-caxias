package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaRegimeEscolar;

public interface EscolaRegimeEscolarRepository extends JpaRepository<EscolaRegimeEscolar, Long>{
	
	@Query("select escolaRegimeEscolar from EscolaRegimeEscolar escolaRegimeEscolar join escolaRegimeEscolar.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaRegimeEscolar>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
