package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.TurnoProfessor;

@Repository
public interface TurnoProfessorRepository extends JpaRepository<TurnoProfessor, Long> {

	List<TurnoProfessor> findByTurnoProfessor(String turnoProfessor);
}
