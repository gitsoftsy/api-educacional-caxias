package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaFonteEnergiaEletrica;
import br.com.softsy.educacional.model.EscolaLingua;

public interface EscolaLinguaRepository extends JpaRepository<EscolaLingua, Long>{

	@Query("select escolaLingua from EscolaLingua escolaLingua join escolaLingua.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaLingua>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
}
