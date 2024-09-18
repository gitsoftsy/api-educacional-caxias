package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.TipoAviso;

@Repository
public interface TipoAvisoRepository extends JpaRepository<TipoAviso, Long>{
	
	@Query("select tipoAviso from TipoAviso join tipoAviso.conta conta where conta.idConta = :idConta")
    Optional<List<TipoAviso>> findByConta_IdConta(@Param("idConta") Long idConta);

}
