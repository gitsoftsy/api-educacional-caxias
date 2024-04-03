package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	
	List<Professor> findByCodigoInep(String codigoInep);
	List<Professor> findByMatricula(String matricula);

}
