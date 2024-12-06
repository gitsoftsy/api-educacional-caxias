package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AvisoInternoDestinatarioResposta;

@Repository
public interface AvisoInternoDestinatarioRespostaRepository extends JpaRepository<AvisoInternoDestinatarioResposta, Long>{

	@Query("select avisoInternoDestinatarioResposta from AvisoInternoDestinatarioResposta join avisoInternoDestinatarioResposta.avisoInternoDestinatario avisoInternoDestinatario where avisoInternoDestinatario.idAvisoInternoDestinatario = :idAvisoInternoDestinatario")
    Optional<List<AvisoInternoDestinatarioResposta>> findByAvisoInternoDestinatario_IdAvisoInternoDestinatario(@Param("idAvisoInternoDestinatario") Long idAvisoInternoDestinatario);
}
