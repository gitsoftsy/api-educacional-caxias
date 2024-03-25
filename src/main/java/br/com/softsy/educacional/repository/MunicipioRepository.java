package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

	@Query("select municipio from Municipio municipio join municipio.uf uf where uf.idUf = :idUf")
	Optional<List<Municipio>> findByUf_IdUf(@Param("idUf") Long idUf);

}
