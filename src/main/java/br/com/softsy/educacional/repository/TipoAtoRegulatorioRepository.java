package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.TipoAtoRegulatorio;

@Repository
public interface TipoAtoRegulatorioRepository extends JpaRepository<TipoAtoRegulatorio, Long>{
	
	List<TipoAtoRegulatorio> findByTipoAtoRegulatorio (String tipoAtoRegulatorio);
	
	@Query("select tipoAtoRegulatorio from TipoAtoRegulatorio tipoAtoRegulatorio join tipoAtoRegulatorio.conta conta where conta.idConta = :idConta")
    Optional<List<TipoAtoRegulatorio>> findByConta_IdConta(@Param("idConta") Long idConta);

}
