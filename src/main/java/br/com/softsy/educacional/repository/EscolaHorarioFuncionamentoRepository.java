package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaHorarioFuncionamento;
import br.com.softsy.educacional.model.EscolaLicSanitario;

public interface EscolaHorarioFuncionamentoRepository extends JpaRepository<EscolaHorarioFuncionamento, Long>{

	
	@Query("select escolaHorarioFuncionamento from EscolaHorarioFuncionamento escolaHorarioFuncionamento join escolaHorarioFuncionamento.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaHorarioFuncionamento>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
}
