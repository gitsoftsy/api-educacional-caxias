package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.ContaLogo;

@Repository
public interface ContaLogoRepository extends JpaRepository<ContaLogo, Long> {

	Optional<ContaLogo> findByContaIdContaAndAplicacaoIdAplicacao(Long idConta, Long idAplicacao);

	List<ContaLogo> findByConta_IdContaAndAplicacao_Aplicacao(Long idConta, String aplicacao);

	List<ContaLogo> findByConta(Long idConta);

}