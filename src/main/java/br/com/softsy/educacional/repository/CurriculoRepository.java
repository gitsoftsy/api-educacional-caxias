package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.Curriculo;

public interface CurriculoRepository extends JpaRepository<Curriculo, Long>{
	
	List<Curriculo> findByCurriculo (String curriculo);

}
