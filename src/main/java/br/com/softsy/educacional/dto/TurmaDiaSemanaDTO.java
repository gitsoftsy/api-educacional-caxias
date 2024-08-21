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
    private Long turmaId;
    
    @NotNull
    private Integer diaSemana;
    private Time horaInicio;
    @NotNull
    private Character permiteChoqueHorario;
    private Time horaFim;
    private LocalDateTime dataCadastro;
	private Character ativo;
	
	private TurmaDTO turma;

    public TurmaDiaSemanaDTO(TurmaDiaSemana turmaDiaSemana) {
        this.idTurmaDiaSemana = turmaDiaSemana.getIdTurmaDiaSemana();
        this.turmaId = turmaDiaSemana.getTurma().getIdTurma();
        this.diaSemana = turmaDiaSemana.getDiaSemana();
        this.horaInicio = turmaDiaSemana.getHoraInicio();
        this.permiteChoqueHorario = turmaDiaSemana.getPermiteChoqueHorario();
        this.horaFim = turmaDiaSemana.getHoraFim();
        this.dataCadastro = turmaDiaSemana.getDataCadastro();
        this.ativo = turmaDiaSemana.getAtivo();
        this.turma = new TurmaDTO(turmaDiaSemana.getTurma());
    }
}
