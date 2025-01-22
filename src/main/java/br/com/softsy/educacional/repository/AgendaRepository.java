package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long>{
	
    @Procedure(name = "PROC_LISTAR_AGENDA_POR_TURMA_E_CONTA")
    List<Object[]> listarAgendaPorTurmaEConta(
            @Param("P_ID_TURMA") Long idTurma,
            @Param("P_ID_CONTA") Long idConta
    );

}
