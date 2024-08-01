package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Curriculo;
import br.com.softsy.educacional.model.GradeCurricular;

@Repository
public interface GradeCurricularRepository extends JpaRepository<GradeCurricular, Long>{

	@Query("select gradeCurricular from GradeCurricular join gradeCurricular.curriculo curriculo where curriculo.idCurriculo = :idCurriculo")
    Optional<List<GradeCurricular>> findByCurriculo_IdCurriculo(@Param("idCurriculo") Long idCurriculo);
	
}
