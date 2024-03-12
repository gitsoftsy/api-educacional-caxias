package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.TipoDependencia;

public interface TipoDependenciaRepository   extends JpaRepository<TipoDependencia, Long>{

	List<TipoDependencia> findByTipoDependencia(String tipoDependencia);
}
