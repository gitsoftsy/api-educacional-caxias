package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.MotivoReprovacaoCandidato;

public interface MotivoReprovacaoCandidatoRepository extends JpaRepository<MotivoReprovacaoCandidato, Long>{
	
	@Query("select motivoReprovacaoCandidato from MotivoReprovacaoCandidato join motivoReprovacaoCandidato.conta conta where conta.idConta = :idConta")
    Optional<List<MotivoReprovacaoCandidato>> findByConta_IdConta(@Param("idConta") Long idConta);

}
