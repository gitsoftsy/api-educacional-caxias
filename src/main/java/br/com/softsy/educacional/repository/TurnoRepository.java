package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long>{
	
	List<Turno> findByTurno(String turno);
	List<Turno> findByMnemonico(String mnemonico);
	
	@Query("select turno from Turno turno join turno.conta conta where conta.idConta = :idConta")
    Optional<List<Turno>> findByConta_idConta(@Param("idConta") Long idConta);

}
