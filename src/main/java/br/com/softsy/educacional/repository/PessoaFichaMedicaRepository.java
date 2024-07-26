package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.LinguaEnsino;
import br.com.softsy.educacional.model.PessoaFichaMedica;

public interface PessoaFichaMedicaRepository extends JpaRepository<PessoaFichaMedica, Long>{
	
	@Query("select pessoaFichaMedica from PessoaFichaMedica pessoaFichaMedica join pessoaFichaMedica.pessoa pessoa where pessoa.idPessoa = :idPessoa")
    Optional<List<PessoaFichaMedica>> findByPessoa_IdPessoa(@Param("idPessoa") Long idPessoa);
	
    @Procedure(name = "PROC_LISTA_FICHA_MEDICA_RESPONSAVEL")
    List<Object[]> listaFichaMedicaResponsavel(
            @Param("P_ID_PESSOA") Long idPessoa
    );

}
