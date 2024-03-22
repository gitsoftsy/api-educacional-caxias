package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.FormaOrganEnsino;

@Repository
public interface FormaOrganEnsinoRepository extends JpaRepository<FormaOrganEnsino, Long> {

	List<FormaOrganEnsino> findByFormaOrganEnsino(String FormaOrganEnsino);
}
