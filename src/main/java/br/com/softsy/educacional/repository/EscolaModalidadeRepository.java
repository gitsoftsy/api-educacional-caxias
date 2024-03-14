package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaModalidade;

public interface EscolaModalidadeRepository extends JpaRepository<EscolaModalidade, Long>{
	
	@Query("select escolaModalidade from EscolaModalidade escolaModalidade join escolaModalidade.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaModalidade>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
