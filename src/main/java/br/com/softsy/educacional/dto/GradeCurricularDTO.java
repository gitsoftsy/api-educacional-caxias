package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.GradeCurricular;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradeCurricularDTO {

    private Long idGradeCurricular;
    private SerieDTO serie;
    private DisciplinaDTO disciplina;
    private CurriculoDTO curriculo;
    private Character obrigatoria;
    private Character retemSerie;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public GradeCurricularDTO(GradeCurricular gradeCurricular) {
        this.idGradeCurricular = gradeCurricular.getIdGradeCurricular();
        this.serie = new SerieDTO(gradeCurricular.getSerie());
        this.disciplina = new DisciplinaDTO(gradeCurricular.getDisciplina());
        this.curriculo = new CurriculoDTO(gradeCurricular.getCurriculo());
        this.obrigatoria = gradeCurricular.getObrigatoria();
        this.retemSerie = gradeCurricular.getRetemSerie();
        this.dataCadastro = gradeCurricular.getDataCadastro();
        this.ativo = gradeCurricular.getAtivo();
    }
}