package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.PeriodoLetivo;

public interface PeriodoLetivoRepository extends JpaRepository<PeriodoLetivo, Long> {

	List<PeriodoLetivo> findByDescricao(String descricao);

	@Query("select periodoLetivo from PeriodoLetivo periodoLetivo join periodoLetivo.conta conta where conta.idConta = :idConta")
	Optional<List<PeriodoLetivo>> findByConta_IdConta(@Param("idConta") Long idConta);

	@Query("select periodoLetivo from PeriodoLetivo periodoLetivo " + "join periodoLetivo.conta conta "
			+ "where conta.idConta = :idConta " + "and periodoLetivo.ano = :ano " + "and periodoLetivo.ativo = 'S' " +

			"order by periodoLetivo.ano desc")
	Optional<List<PeriodoLetivo>> findByConta_IdContaAndAno(@Param("idConta") Long idConta, @Param("ano") Integer ano);

	@Query("SELECT COUNT(p) > 0 FROM PeriodoLetivo p JOIN p.conta c WHERE p.idPeriodoLetivo = :idPeriodoLetivo AND c.idConta = :idConta")
	boolean existsByIdPeriodoLetivoAndContaIdConta(@Param("idPeriodoLetivo") Long idPeriodoLetivo, @Param("idConta") Long idConta);


}
