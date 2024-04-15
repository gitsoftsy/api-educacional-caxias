package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.GradeCurricular;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradeCurricularDTO {

    private Long idGradeCurricular;
    private CursoSerieDTO cursoSerie;
    private TurnoDTO turno;
    private DisciplinaDTO disciplina;
    private CurriculoDTO curriculo;
    private Character obrigatoria;
    private Character retemSerie;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public GradeCurricularDTO(GradeCurricular gradeCurricular) {
        this.idGradeCurricular = gradeCurricular.getIdGradeCurricular();
        this.cursoSerie = new CursoSerieDTO(gradeCurricular.getCursoSerie());
        this.turno = new TurnoDTO(gradeCurricular.getTurno());
        this.disciplina = new DisciplinaDTO(gradeCurricular.getDisciplina());
        this.curriculo = new CurriculoDTO(gradeCurricular.getCurriculo());
        this.obrigatoria = gradeCurricular.getObrigatoria();
        this.retemSerie = gradeCurricular.getRetemSerie();
        this.dataCadastro = gradeCurricular.getDataCadastro();
        this.ativo = gradeCurricular.getAtivo();
    }
}