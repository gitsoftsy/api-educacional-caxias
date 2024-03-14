package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import br.com.softsy.educacional.model.EscolaFornecimentoAgua;

public interface EscolaFornecimentoAguaRepository extends JpaRepository<EscolaFornecimentoAgua, Long>{
	
	@Query("select escolaFornecimentoAgua from EscolaFornecimentoAgua escolaFornecimentoAgua join escolaFornecimentoAgua.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaFornecimentoAgua>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
