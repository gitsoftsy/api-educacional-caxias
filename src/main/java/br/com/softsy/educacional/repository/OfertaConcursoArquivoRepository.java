package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.OfertaConcursoArquivo;
import br.com.softsy.educacional.model.OfertaConcursoImg;

@Repository
public interface OfertaConcursoArquivoRepository extends JpaRepository<OfertaConcursoArquivo, Long>{
	
	 List<OfertaConcursoArquivo> findByOfertaConcurso_IdOfertaConcursoAndOrdem(Long ofertaConcursoId, Integer ordem);
	 
	 List<OfertaConcursoArquivo> findByOfertaConcurso_IdOfertaConcurso(Long ofertaConcursoId);
	 
	 List<OfertaConcursoArquivo> findByOfertaConcurso_IdOfertaConcursoOrderByOrdem(Long idOfertaConcurso);

}