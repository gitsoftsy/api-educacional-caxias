package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.OfertaConcursoArquivo;
import br.com.softsy.educacional.model.OfertaConcursoImg;

@Repository
public interface OfertaConcursoImgRepository extends JpaRepository<OfertaConcursoImg, Long> {

	List<OfertaConcursoImg> findByOfertaConcurso_IdOfertaConcursoAndOrdem(Long idOfertaConcurso, Integer ordem);

	List<OfertaConcursoImg> findByOfertaConcurso_IdOfertaConcurso(Long ofertaConcursoId);

	List<OfertaConcursoImg> findByOfertaConcurso_IdOfertaConcursoAndConta_IdContaOrderByOrdem(Long idOfertaConcurso,
			Long idConta);

	List<OfertaConcursoImg> findByOfertaConcurso_IdOfertaConcursoOrderByOrdem(Long idOfertaConcurso);

}
