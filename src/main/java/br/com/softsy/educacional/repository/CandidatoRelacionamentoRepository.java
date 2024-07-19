package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.CandidatoRelacionamento;

@Repository
public interface CandidatoRelacionamentoRepository extends JpaRepository<CandidatoRelacionamento, Long>{
	
	@Query("select candidatoRelacionamento from CandidatoRelacionamento candidatoRelacionamento join candidatoRelacionamento.pessoa pessoa where pessoa.idPessoa = :idPessoa")
    Optional<List<CandidatoRelacionamento>> findByPessoa_IdPessoa(@Param("idPessoa") Long idPessoa);
	

}
