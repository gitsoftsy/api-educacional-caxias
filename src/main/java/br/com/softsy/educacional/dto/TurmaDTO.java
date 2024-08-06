package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Turma;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaDTO {

    private Long idTurma;
    private EscolaDTO escola;
    private PeriodoLetivoDTO periodoLetivo;
    private TurnoDTO turno;
    @NotNull
    private String nomeTurma;
    private String codTurmaInep;
    private GradeCurricularDTO gradeCurricular;
    @NotNull
    private Character libras;
    private LocalDateTime dataCadastro;
    @NotNull
    private Character ativo;
    @NotNull
    private Integer vagas;
    @NotNull
    private Character controlaVagas;

    public TurmaDTO(Turma turma) {
        this.idTurma = turma.getIdTurma();
        this.escola = new EscolaDTO(turma.getEscola());
        this.periodoLetivo = new PeriodoLetivoDTO(turma.getPeriodoLetivo());
        this.turno = new TurnoDTO(turma.getTurno());
        this.nomeTurma = turma.getNomeTurma();
        this.codTurmaInep = turma.getCodTurmaInep();
        this.gradeCurricular = new GradeCurricularDTO(turma.getGradeCurricular());
        this.libras = turma.getLibras();
        this.dataCadastro = turma.getDataCadastro();
        this.ativo = turma.getAtivo();
        this.vagas = turma.getVagas();
        this.controlaVagas = turma.getControlaVagas();
    }
}
