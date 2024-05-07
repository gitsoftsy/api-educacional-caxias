package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.TratamentoLixo;

public interface TratamentoLixoRepository    extends JpaRepository<TratamentoLixo, Long>{

	List<TratamentoLixo> findByTratamentoLixo(String tratamentoLixo);
	
	@Query("select tratamentoLixo from TratamentoLixo tratamentoLixo join tratamentoLixo.conta conta where conta.idConta = :idConta")
    Optional<List<TratamentoLixo>> findByConta_IdConta(@Param("idConta") Long idConta);

}
