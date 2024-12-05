package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


import br.com.softsy.educacional.model.AvisoInternoDestinatario;

public interface AvisoInternoDestinatarioRepository extends JpaRepository<AvisoInternoDestinatario, Long>{

//	@Query("select avisoInternoDestinatario from AvisoInternoDestinatario join avisoInternoDestinatario.usuario usuario where usuario.idUsuario = :idUsuario")
//    Optional<List<AvisoInternoDestinatario>> findByUsuario_IdUsuario(@Param("idUsuario") Long idUsuario);
//	
//	@Query("select avisoInternoDestinatario from AvisoInternoDestinatario join avisoInternoDestinatario.usuario professor where professor.idProfessor = :idProfessor")
//    Optional<List<AvisoInternoDestinatario>> findByProfessor_IdProfessor(@Param("idProfessor") Long idProfessor);
	
//	  @Procedure(name = "PROC_LISTAR_DESTINATARIOS_AVISO_INTERNO")
//	    List<Object[]> listaDestinatariosAvisoInterno(
//	            @Param("P_ID_AVISO_INTERNO") Long idAvisointerno
//	    );
}
