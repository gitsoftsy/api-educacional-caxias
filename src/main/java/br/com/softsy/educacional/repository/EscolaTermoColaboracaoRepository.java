package br.com.softsy.educacional.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaTermoColaboracao;


@Repository
public interface EscolaTermoColaboracaoRepository extends JpaRepository< EscolaTermoColaboracao, Long> {

	@Query("select escolaTermoColaboracao from EscolaTermoColaboracao escolaTermoColaboracao join escolaTermoColaboracao.escola escola where escola.idEscola = :idEscola")
	Optional<List<EscolaTermoColaboracao>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
