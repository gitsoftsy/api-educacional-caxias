package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Curriculo;

public interface CurriculoRepository extends JpaRepository<Curriculo, Long>{
	
	List<Curriculo> findByCurriculo (String curriculo);

	@Query("select curriculo from Curriculo join curriculo.curso curso where curso.idCurso = :idCurso")
    Optional<List<Curriculo>> findByCurso_IdCurso(@Param("idCurso") Long idCurso);
	
	Optional<List<Curriculo>> findByConta_IdContaAndCurso_IdCursoAndAtivo(Long idConta, Long idCurso, Character ativo);

}

