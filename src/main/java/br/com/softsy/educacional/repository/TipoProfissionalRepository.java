package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.TipoProfissional;

public interface TipoProfissionalRepository extends JpaRepository<TipoProfissional, Long>{
	
	List<TipoProfissional> findByTipoProfissional (String tipoProfissional);

}
