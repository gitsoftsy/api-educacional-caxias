package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.InstrPedagogico;

public interface InstrPedagogicoRepository extends JpaRepository<InstrPedagogico, Long>{

	List<InstrPedagogico> findByInstrPedagogico (String instrPedagogico);
	
}
