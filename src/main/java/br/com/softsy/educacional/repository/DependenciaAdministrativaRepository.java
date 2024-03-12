package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.DependenciaAdministrativa;

public interface DependenciaAdministrativaRepository extends JpaRepository<DependenciaAdministrativa, Long>{

	List<DependenciaAdministrativa> findByDependenciaAdministrativa(String dependenciaAdministrativa);
}
