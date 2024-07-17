package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.CandidatoDocumentoIngresso;
import br.com.softsy.educacional.model.ContaPadraoAcesso;

public interface CandidatoDocumentoIngressoRepository extends JpaRepository<CandidatoDocumentoIngresso, Long>{

	@Query("select candidatoDocumentoIngresso from CandidatoDocumentoIngresso candidatoDocumentoIngresso join candidatoDocumentoIngresso.candidato candidato where candidato.idCandidato = :idCandidato")
    Optional<List<CandidatoDocumentoIngresso>> findByCandidato_IdCandidato(@Param("idCandidato") Long idCandidato);
	
}
