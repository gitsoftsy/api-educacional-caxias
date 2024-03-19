package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>{

	List<Equipamento> findByEquipamento (String equipamento);
}
