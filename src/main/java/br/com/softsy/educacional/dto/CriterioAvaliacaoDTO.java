package br.com.softsy.educacional.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.CriterioAvaliacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CriterioAvaliacaoDTO {

    private Long idCriterioAvaliacao;

    @NotNull
    private Long turmaId;

    @NotNull
    private String criterioAvaliacao;

    @NotNull
    private Integer ordem;

    @NotNull
    private LocalDate dataProva;

    private Character ativo;

    public CriterioAvaliacaoDTO(CriterioAvaliacao criterioAvaliacao) {
        this.idCriterioAvaliacao = criterioAvaliacao.getIdCriterioAvaliacao();
        this.turmaId = criterioAvaliacao.getTurma().getIdTurma();
        this.criterioAvaliacao = criterioAvaliacao.getCriterioAvaliacao();
        this.ordem = criterioAvaliacao.getOrdem();
        this.dataProva = criterioAvaliacao.getDataProva();
        this.ativo = criterioAvaliacao.getAtivo();
    }
}