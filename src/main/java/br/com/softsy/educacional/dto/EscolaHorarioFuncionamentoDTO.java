package br.com.softsy.educacional.dto;

import java.sql.Time;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaHorarioFuncionamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaHorarioFuncionamentoDTO {
    private Long idEscolaHorarioFuncionamento;
    private Long escolaId;
    @NotNull
    private Integer diaSemana;
    @NotNull
    private Time horaInicio;
    @NotNull
    private Time horaFim;
    private LocalDateTime dataCadastro;

    private Character ativo;
    
    public EscolaHorarioFuncionamentoDTO(EscolaHorarioFuncionamento horarioFuncionamento) {
        this.idEscolaHorarioFuncionamento = horarioFuncionamento.getIdEscolaHorarioFuncionamento();
        this.escolaId = horarioFuncionamento.getEscola().getIdEscola();
        this.diaSemana = horarioFuncionamento.getDiaSemana();
        this.horaInicio = horarioFuncionamento.getHoraInicio();
        this.horaFim = horarioFuncionamento.getHoraFim();
        this.dataCadastro = horarioFuncionamento.getDataCadastro();
        this.ativo = horarioFuncionamento.getAtivo();
    }
}
