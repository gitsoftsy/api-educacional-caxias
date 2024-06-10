package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.OfertaConcurso;

@Repository
public interface OfertaConcursoRepository extends JpaRepository<OfertaConcurso, Long>{

	@Query("select ofertaConcurso from OfertaConcurso ofertaConcurso join ofertaConcurso.concurso concurso where concurso.idConcurso = :idConcurso")
    Optional<List<OfertaConcurso>> findByConcurso_idConcurso(@Param("idConcurso") Long idConcurso);

	
}
