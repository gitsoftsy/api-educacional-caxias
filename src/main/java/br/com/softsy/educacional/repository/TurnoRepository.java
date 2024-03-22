package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long>{
	
	List<Turno> findByTurno(String turno);
	List<Turno> findByMnemonico(String mnemonico);

}
