package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Uf;

public interface UfRepository extends JpaRepository<Uf, Long>{

	List<Uf> findByCodUf (String codUf);
}
