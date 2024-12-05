package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


import br.com.softsy.educacional.model.AvisoInternoDestinatario;

public interface AvisoInternoDestinatarioRepository extends JpaRepository<AvisoInternoDestinatario, Long>{

	 @Query("SELECT a FROM AvisoInternoDestinatario a JOIN a.usuarioDestinatario u WHERE u.idUsuario = :idUsuario")
	    Optional<List<AvisoInternoDestinatario>> findByUsuarioDestinatario_IdUsuario(@Param("idUsuario") Long idUsuario);

	    @Query("SELECT a FROM AvisoInternoDestinatario a JOIN a.professorDestinatario p WHERE p.idProfessor = :idProfessor")
	    Optional<List<AvisoInternoDestinatario>> findByProfessorDestinatario_IdProfessor(@Param("idProfessor") Long idProfessor);
	
	  @Procedure(name = "PROC_AVISO_LISTA_DESTINATARIO_INTERNO")
	    List<Object[]> listaDestinatariosAvisoInterno(
	            @Param("P_ID_AVISO_INTERNO") Long idAvisointerno
	    );
}
