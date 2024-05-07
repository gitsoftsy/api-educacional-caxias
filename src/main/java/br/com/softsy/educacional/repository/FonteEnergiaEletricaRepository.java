package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.FonteEnergiaEletrica;

@Repository
public interface FonteEnergiaEletricaRepository extends JpaRepository<FonteEnergiaEletrica, Long>{

	List<FonteEnergiaEletrica> findByFonteEnergiaEletrica (String fonteEnergiaEletrica);
	
	@Query("select fonteEnergiaEletrica fromFonteEnergiaEletrica fonteEnergiaEletrica join fonteEnergiaEletrica.conta conta where conta.idConta = :idConta")
    Optional<List<FonteEnergiaEletrica>> findByConta_IdConta(@Param("idConta") Long idConta);
}
