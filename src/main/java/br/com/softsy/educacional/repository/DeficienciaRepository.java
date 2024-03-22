package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Deficiencia;

@Repository
public interface DeficienciaRepository extends JpaRepository<Deficiencia, Long> {

	List<Deficiencia> findByDeficiencia(String deficiencia);
}