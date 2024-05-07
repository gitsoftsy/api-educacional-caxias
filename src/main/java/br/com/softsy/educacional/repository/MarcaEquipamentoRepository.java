package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.MarcaEquipamento;
public interface MarcaEquipamentoRepository extends JpaRepository<MarcaEquipamento, Long>{
	
	List<MarcaEquipamento> findByMarcaEquipamento (String marcaEquipamento);
	
	@Query("select marcaEquipamento from MarcaEquipamento marcaEquipamento join marcaEquipamento.conta conta where conta.idConta = :idConta")
    Optional<List<MarcaEquipamento>> findByConta_IdConta(@Param("idConta") Long idConta);

}
