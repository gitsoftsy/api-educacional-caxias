package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AvisoDestinatario;
import br.com.softsy.educacional.model.AvisoDestinatarioResposta;

@Repository
public interface AvisoDestinatarioRespostaRepository extends JpaRepository<AvisoDestinatarioResposta, Long>{
	
	@Query("select avisoDestinatarioResposta from AvisoDestinatarioResposta join avisoDestinatarioResposta.avisoDestinatario avisoDestinatario where avisoDestinatario.idAvisoDestinatario = :idAvisoDestinatario")
    Optional<List<AvisoDestinatarioResposta>> findByAvisoDestinatario_IdAvisoDestinatario(@Param("idAvisoDestinatario") Long idAvisoDestinatario);

}
