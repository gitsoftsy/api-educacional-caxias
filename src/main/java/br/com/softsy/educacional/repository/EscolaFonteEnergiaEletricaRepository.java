package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaFonteEnergiaEletrica;
import br.com.softsy.educacional.model.EscolaFornecimentoAgua;

public interface EscolaFonteEnergiaEletricaRepository extends JpaRepository<EscolaFonteEnergiaEletrica, Long>{

	@Query("select escolaFonteEnergiaEletrica from EscolaFonteEnergiaEletrica escolaFonteEnergiaEletrica join escolaFonteEnergiaEletrica.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaFonteEnergiaEletrica>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
}
