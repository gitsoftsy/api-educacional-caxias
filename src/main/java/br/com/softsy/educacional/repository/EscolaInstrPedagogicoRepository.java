package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaInstrPedagogico;

public interface EscolaInstrPedagogicoRepository extends JpaRepository<EscolaInstrPedagogico, Long>{

	@Query("select escolaInstrPedagogico from EscolaInstrPedagogico escolaInstrPedagogico join escolaInstrPedagogico.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaInstrPedagogico>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
