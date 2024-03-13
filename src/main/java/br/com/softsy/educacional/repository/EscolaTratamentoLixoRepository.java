package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaTelefone;
import br.com.softsy.educacional.model.EscolaTratamentoLixo;

public interface EscolaTratamentoLixoRepository extends JpaRepository<EscolaTratamentoLixo, Long>{

	@Query("select escolaTratamentoLixo from EscolaTratamentoLixo escolaTratamentoLixo join escolaTratamentoLixo.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaTratamentoLixo>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
