package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AgendaAnexo;
import br.com.softsy.educacional.model.Candidato;
@Repository
public interface AgendaAnexoRepository extends JpaRepository<AgendaAnexo, Long>{
	
	@Query("select agendaAnexo from AgendaAnexo join agendaAnexo.agenda agenda where agenda.idAgenda = :idAgenda")
    Optional<List<AgendaAnexo>> findByAgenda_IdAgenda(@Param("idAgenda") Long idAgenda);

}
