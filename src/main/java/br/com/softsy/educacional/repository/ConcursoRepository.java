package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Concurso;
@Repository
public interface ConcursoRepository extends JpaRepository<Concurso, Long>{

	@Query("select concurso from Concurso concurso join concurso.conta conta where conta.idConta = :idConta")
    Optional<List<Concurso>> findByConta_IdConta(@Param("idConta") Long idConta);
	
	Optional<List<Concurso>> findByConta_IdContaAndAtivo(Long idConta, Character ativo);
	

}
