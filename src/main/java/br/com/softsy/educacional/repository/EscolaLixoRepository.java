package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaLixo;

@Repository
public interface EscolaLixoRepository extends JpaRepository<EscolaLixo, Long>{

	@Query("select escolaLixo from EscolaLixo escolaLixo join escolaLixo.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaLixo>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
