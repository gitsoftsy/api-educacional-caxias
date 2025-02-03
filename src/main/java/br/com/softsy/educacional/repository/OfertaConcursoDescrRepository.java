package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.OfertaConcursoDescr;

@Repository
public interface OfertaConcursoDescrRepository extends JpaRepository<OfertaConcursoDescr, Long>{
	
	boolean existsByOrdemAndOfertaConcursoAndConta(Integer ordem, OfertaConcurso ofertaConcurso, Conta conta);
	
	List<OfertaConcursoDescr> findByOfertaConcursoIdOfertaConcurso(Long IdOfertaConcurso);


}
