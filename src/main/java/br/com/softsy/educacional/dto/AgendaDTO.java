package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Agenda;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgendaDTO {

    private Long idAgenda;

    @NotNull
    private Long turmaId;

    @NotNull
    private LocalDate dataAgenda;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFim;

    @NotNull
    private Character realizada;

    private String tituloAula;

    private String resumoAula;

    private Character ativo;
    
    private TurmaDTO turma;

    public AgendaDTO(Agenda agenda) {
        this.idAgenda = agenda.getIdAgenda();
        this.turmaId = agenda.getTurma().getIdTurma();
        this.dataAgenda = agenda.getDataAgenda();
        this.horaInicio = agenda.getHoraInicio();
        this.horaFim = agenda.getHoraFim();
        this.realizada = agenda.getRealizada();
        this.tituloAula = agenda.getTituloAula();
        this.resumoAula = agenda.getResumoAula();
        this.ativo = agenda.getAtivo();
        this.turma = new TurmaDTO(agenda.getTurma());
    }
}