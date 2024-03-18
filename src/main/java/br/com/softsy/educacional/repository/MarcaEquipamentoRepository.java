package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.MarcaEquipamento;

public interface MarcaEquipamentoRepository extends JpaRepository<MarcaEquipamento, Long>{
	
	List<MarcaEquipamento> findByMarcaEquipamento (String marcaEquipamento);

}
