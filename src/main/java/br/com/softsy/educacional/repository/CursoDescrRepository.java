package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.CursoDescr;

@Repository
public interface CursoDescrRepository extends JpaRepository<CursoDescr, Long>{
	
	boolean existsByOrdemAndCursoAndConta(Integer ordem, Curso curso, Conta conta);
	
	List<CursoDescr> findByCursoIdCurso(Long idCurso);

}
