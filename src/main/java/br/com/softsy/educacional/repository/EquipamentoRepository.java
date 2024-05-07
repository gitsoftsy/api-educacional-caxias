package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>{

	List<Equipamento> findByEquipamento (String equipamento);
	
	@Query("select equipamento from Zoneamento equipamento join equipamento.conta conta where conta.idConta = :idConta")
    Optional<List<Equipamento>> findByConta_IdConta(@Param("idConta") Long idConta);
}
