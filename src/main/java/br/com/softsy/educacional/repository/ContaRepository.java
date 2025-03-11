package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.CursoImg;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

	List<Conta> findByConta (String conta);
	
	boolean existsById(Long idConta);
	
}
