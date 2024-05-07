package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.InstrPedagogico;

public interface InstrPedagogicoRepository extends JpaRepository<InstrPedagogico, Long>{

	List<InstrPedagogico> findByInstrPedagogico (String instrPedagogico);
	
	@Query("select instrPedagogico from InstrPedagogico instrPedagogico join instrPedagogico.conta conta where conta.idConta = :idConta")
    Optional<List<InstrPedagogico>> findByConta_IdConta(@Param("idConta") Long idConta);
	
}
