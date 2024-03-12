package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.ModalidadeEscola;

public interface ModalidadeEscolaRepository   extends JpaRepository<ModalidadeEscola, Long>{

		List<ModalidadeEscola> findByModalidadeEscola(String modalidadeEscola);

}