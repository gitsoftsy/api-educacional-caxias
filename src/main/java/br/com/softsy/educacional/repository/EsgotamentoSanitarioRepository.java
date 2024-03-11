package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EsgotamentoSanitario;

@Repository
public interface EsgotamentoSanitarioRepository extends JpaRepository<EsgotamentoSanitario, Long> {

	List<EsgotamentoSanitario> findByEsgotamentoSanitario (String esgotamentoSanitario);
}
