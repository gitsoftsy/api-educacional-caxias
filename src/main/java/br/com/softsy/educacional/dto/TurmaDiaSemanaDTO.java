package br.com.softsy.educacional.dto;

import java.sql.Time;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TurmaDiaSemana;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaDiaSemanaDTO {
    private Long idTurmaDiaSemana;
    
    @NotNull
    private Long turmaProfessorId;
    
    private Long escolaDependenciaId;
    @NotNull
    private Integer diaSemana;
    private Time horaInicio;
    @NotNull
    private Character permiteChoqueHorario;
    private Time horaFim;
    private LocalDateTime dataCadastro;

    public TurmaDiaSemanaDTO(TurmaDiaSemana turmaDiaSemana) {
        this.idTurmaDiaSemana = turmaDiaSemana.getIdTurmaDiaSemana();
        this.escolaDependenciaId = turmaDiaSemana.getEscolaDependencia().getIdEscolaDependencia();
        this.turmaProfessorId = turmaDiaSemana.getTurmaProfessor().getIdTurmaProfessor();
        this.diaSemana = turmaDiaSemana.getDiaSemana();
        this.horaInicio = turmaDiaSemana.getHoraInicio();
        this.permiteChoqueHorario = turmaDiaSemana.getPermiteChoqueHorario();
        this.horaFim = turmaDiaSemana.getHoraFim();
        this.dataCadastro = turmaDiaSemana.getDataCadastro();
    }
}
