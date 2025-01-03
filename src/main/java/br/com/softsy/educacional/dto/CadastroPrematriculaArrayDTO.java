package br.com.softsy.educacional.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroPrematriculaArrayDTO {

    @NotNull
    private Long contaId;
    @NotNull
    private Character ativo;
    @NotNull
    private Long tipoMatriculaId;
    @NotNull
    private List<Long> alunosId;
    @NotNull
    private Long periodoLetivoId;
    @NotNull
    private Long disciplinaId;
    private Long turmaId;
    private Long serieId;
    private String mensagemErro;
    @NotNull
    private Character manual;
    private Long usuarioId;
    private String observacao;

}