package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.NivelEscolaridade;

@Repository
public interface NivelEscolaridadeRepository extends JpaRepository<NivelEscolaridade, Long> {

	List<NivelEscolaridade> findByNivelEscolaridade(String nivelEscolaridade);
}
