package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaEsgotamentoSanitario;

public interface EscolaEsgotamentoSanitarioRepository extends JpaRepository<EscolaEsgotamentoSanitario, Long> {

	@Query("select escolaEsgotamentoSanitario from EscolaEsgotamentoSanitario escolaEsgotamentoSanitario join escolaEsgotamentoSanitario.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaEsgotamentoSanitario>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);
}
