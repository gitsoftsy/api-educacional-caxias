package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.FornecimentoAgua;

@Repository
public interface FornecimentoAguaRepository extends JpaRepository<FornecimentoAgua, Long>{

	List<FornecimentoAgua> findByFornecimentoAgua(String fornecimentoAgua);
}
