package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Periodicidade;

public interface PeriodicidadeRepository extends JpaRepository<Periodicidade, Long> {
	
	List<Periodicidade> findByPeriodicidade(String periodicidade);

}
