package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import br.com.softsy.educacional.model.EscolaModalidade;

public interface EscolaDestinacaoLixoRepository extends JpaRepository<EscolaDestinacaoLixo, Long>{
	
	@Query("select escolaDestinacaoLixo from EscolaDestinacaoLixo escolaDestinacaoLixo join escolaDestinacaoLixo.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaDestinacaoLixo>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
