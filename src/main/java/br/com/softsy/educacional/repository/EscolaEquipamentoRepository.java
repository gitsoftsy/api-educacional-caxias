package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaEquipamento;

public interface EscolaEquipamentoRepository extends JpaRepository<EscolaEquipamento, Long>{

	@Query("select escolaEquipamento from EscolaEquipamento escolaEquipamento join escolaEquipamento.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaEquipamento>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
	
}
