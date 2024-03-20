package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaDependencia;

public interface EscolaDependenciaRepository extends JpaRepository<EscolaDependencia, Long>{
	
	@Query("select escolaDependencia from EscolaDependencia escolaDependencia join escolaDependencia.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaDependencia>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
