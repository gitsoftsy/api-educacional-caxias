package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
	
	List<Curso> findByCodCurso(String codCurso);
	List<Curso> findByCodCursoInpe(String codCursoInpe);
	
	@Query("select curso from Curso curso join curso.escola escola where escola.idEscola = :idEscola")
    Optional<List<Curso>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
