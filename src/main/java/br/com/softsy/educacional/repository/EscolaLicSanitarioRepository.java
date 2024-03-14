package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import br.com.softsy.educacional.model.EscolaLicSanitario;

public interface EscolaLicSanitarioRepository extends JpaRepository<EscolaLicSanitario, Long>{

	@Query("select escolaLicSanitario from EscolaLicSanitario escolaLicSanitario join escolaLicSanitario.escola escola where escola.idEscola = :idEscola")
    Optional<List<EscolaLicSanitario>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
