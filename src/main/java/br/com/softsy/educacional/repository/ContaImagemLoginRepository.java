package br.com.softsy.educacional.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Aplicacao;
import br.com.softsy.educacional.model.ContaImagemLogin;

@Repository
public interface ContaImagemLoginRepository extends JpaRepository<ContaImagemLogin, Long> {

	List<ContaImagemLogin> findByConta_IdConta(Long idConta);

	List<ContaImagemLogin> findByConta_IdContaAndAplicacao(Long idConta, Aplicacao aplicacao);

	List<ContaImagemLogin> findByConta(Long idConta);

	Optional<ContaImagemLogin> findByIdContaImagemLoginAndConta_IdConta(Long idContaImagemLogin, Long idConta);

	boolean existsById(Long idContaImagemLogin);

}
