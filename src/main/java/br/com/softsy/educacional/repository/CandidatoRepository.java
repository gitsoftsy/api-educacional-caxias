package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.Candidato;


@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
	
	List<Candidato> findByCandidato(String candidato);
	
	@Query("select candidato from Candidato join candidato.conta conta where conta.idConta = :idConta")
    Optional<List<Candidato>> findByConta_IdConta(@Param("idConta") Long idConta);
	
}
