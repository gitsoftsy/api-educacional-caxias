package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.TratamentoLixo;

public interface TratamentoLixoRepository    extends JpaRepository<TratamentoLixo, Long>{

	List<TratamentoLixo> findByTratamentoLixo(String tratamentoLixo);

}
