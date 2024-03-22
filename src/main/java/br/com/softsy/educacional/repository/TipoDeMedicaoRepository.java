package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.TipoDeMedicao;

public interface TipoDeMedicaoRepository extends JpaRepository<TipoDeMedicao, Long>{
	
	List<TipoDeMedicao> findByTipoMedicao(String tipoMedicao);

}
