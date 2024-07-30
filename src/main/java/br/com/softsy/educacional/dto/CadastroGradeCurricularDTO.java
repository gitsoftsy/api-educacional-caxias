package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.GradeCurricular;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroGradeCurricularDTO {

    private Long idGradeCurricular;
    private Long serieId;
    private Long curriculoId;
    private Long disciplinaId;
    private Character obrigatoria;
    private Character retemSerie;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public CadastroGradeCurricularDTO(GradeCurricular gradeCurricular) {
        this.idGradeCurricular = gradeCurricular.getIdGradeCurricular();
        this.serieId = gradeCurricular.getSerie().getIdSerie();
        this.disciplinaId = gradeCurricular.getDisciplina().getIdDisciplina();
        this.curriculoId = gradeCurricular.getCurriculo().getIdCurriculo();
        this.obrigatoria = gradeCurricular.getObrigatoria();
        this.retemSerie = gradeCurricular.getRetemSerie();
        this.dataCadastro = gradeCurricular.getDataCadastro();
        this.ativo = gradeCurricular.getAtivo();
    }
}