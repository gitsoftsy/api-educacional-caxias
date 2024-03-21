package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.AreaConhecimento;

public interface AreaConhecimentoRepository extends JpaRepository<AreaConhecimento, Long>{
	
	List<AreaConhecimento> findByAreaConhecimento(String areaConhecimento);

}
