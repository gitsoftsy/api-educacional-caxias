package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.ModalidadeEscola;
public interface ModalidadeEscolaRepository   extends JpaRepository<ModalidadeEscola, Long>{

		List<ModalidadeEscola> findByModalidadeEscola(String modalidadeEscola);
		
		@Query("select modalidadeEscola from ModalidadeEscola modalidadeEscola join modalidadeEscola.conta conta where conta.idConta = :idConta")
	    Optional<List<ModalidadeEscola>> findByConta_IdConta(@Param("idConta") Long idConta);

}