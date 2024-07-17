package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.MotivoReprovacaoDocumento;

public interface MotivoReprovacaoDocumentoRepository extends JpaRepository<MotivoReprovacaoDocumento, Long>{

	@Query("select motivoReprovacaoDocumento from MotivoReprovacaoDocumento join motivoReprovacaoDocumento.conta conta where conta.idConta = :idConta")
    Optional<List<MotivoReprovacaoDocumento>> findByConta_IdConta(@Param("idConta") Long idConta);
	
}
