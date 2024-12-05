package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AvisoInterno;

@Repository
public interface AvisoInternoRepository extends JpaRepository<AvisoInterno, Long> {

	 @Query("select avisoInterno from AvisoInterno avisoInterno join avisoInterno.professor professor where professor.idProfessor = :idProfessor")
	  Optional<List<AvisoInterno>> findByProfessor_IdProfessor(@Param("idProfessor") Long idProfessor);
	    
	 @Query("select avisoInterno from AvisoInterno join avisoInterno.conta conta where conta.idConta = :idConta")
	   Optional<List<AvisoInterno>> findByConta_IdConta(@Param("idConta") Long idConta);

}
