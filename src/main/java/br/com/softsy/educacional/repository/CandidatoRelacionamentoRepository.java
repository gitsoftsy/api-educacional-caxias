package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.CandidatoRelacionamento;

@Repository
public interface CandidatoRelacionamentoRepository extends JpaRepository<CandidatoRelacionamento, Long>{
	

}
