package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.SituacaoFuncionamento;

public interface SituacaoFuncionamentoRepository   extends JpaRepository<SituacaoFuncionamento, Long>{

	List<SituacaoFuncionamento> findBySituacaoFuncionamento(String situacaoFuncionamento);
}
