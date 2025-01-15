package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.dto.CursoDTO;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.Turno;

public interface CursoRepository extends JpaRepository<Curso, Long>{
	
	List<Curso> findByCodCurso(String codCurso);
	List<Curso> findByCodCursoInpe(String codCursoInpe);
	
	@Query("select curso from Curso curso join curso.conta conta where conta.idConta = :idConta")
    Optional<List<Curso>> findByConta_IdConta(@Param("idConta") Long idConta);
	
	Optional<List<Curso>> findActiveCursoByConta_IdContaAndAtivo(Long idConta, Character ativo);
	
	
}
