package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Utm;

@Repository
public interface UtmRepository extends JpaRepository<Utm, Long>{
	
	List<Utm> findByContaIdContaAndAtivo(Long idConta, Character ativo);

}
