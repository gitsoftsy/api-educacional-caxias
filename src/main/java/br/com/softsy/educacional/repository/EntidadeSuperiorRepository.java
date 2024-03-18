package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.EntidadeSuperior;

public interface EntidadeSuperiorRepository extends JpaRepository<EntidadeSuperior, Long>{

	List<EntidadeSuperior> findByEntidadeSuperior (String entidadeSuperior);
}
