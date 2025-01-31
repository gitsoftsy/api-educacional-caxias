package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.CursoImg;

@Repository
public interface CursoImgRepository extends JpaRepository<CursoImg, Long>{

	  List<CursoImg> findByCurso_IdCursoAndOrdem(Long idCurso, Integer ordem);
	  
	  List<CursoImg> findByCursoIdCursoAndCursoContaIdConta(Long idCurso, Long idConta);
	  
	  List<CursoImg> findByCursoIdCursoAndCursoContaIdContaOrderByOrdem(Long idCurso, Long idConta);

}
