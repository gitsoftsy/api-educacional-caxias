package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long>{
	
	List<Pais> findByCodPais (String codPais);
	List<Pais> findByNomePais (String nomePais);

}
