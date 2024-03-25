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
    private Long turmaId;
    @NotNull
    private Integer diaSemana;
    @NotNull
    private Time horaInicio;
    @NotNull
    private Time horaFim;
    private LocalDateTime dataCadastro;

    public TurmaDiaSemanaDTO(TurmaDiaSemana turmaDiaSemana) {
        this.idTurmaDiaSemana = turmaDiaSemana.getIdTurmaDiaSemana();
        this.turmaId = turmaDiaSemana.getTurma().getIdTurma();
        this.diaSemana = turmaDiaSemana.getDiaSemana();
        this.horaInicio = turmaDiaSemana.getHoraInicio();
        this.horaFim = turmaDiaSemana.getHoraFim();
        this.dataCadastro = turmaDiaSemana.getDataCadastro();
    }
}
