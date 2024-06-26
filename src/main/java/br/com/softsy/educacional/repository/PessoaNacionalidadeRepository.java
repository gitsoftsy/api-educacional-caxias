package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.PessoaNacionalidade;

public interface PessoaNacionalidadeRepository extends JpaRepository<PessoaNacionalidade, Long>{

//	@Query("select pessoaNacionalidade from PessoaNacionalidade pessoaNacionalidade join pessoaNacionalidade.pessoa pessoa where pessoa.idPessoa = :idPessoa")
//    Optional<List<PessoaNacionalidade>> findByPessoaNacionalidade_IdPessoa(@Param("idPessoa") Long idPessoa);
	
}
