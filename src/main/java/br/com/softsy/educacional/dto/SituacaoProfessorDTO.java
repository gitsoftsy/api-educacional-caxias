package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.SituacaoProfessor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SituacaoProfessorDTO {

    private Long idSituacaoProfessor;

    @NotNull
    private String situacaoProfessor;

    private LocalDateTime dataCadastro;

    private Character ativo;

    public SituacaoProfessorDTO(SituacaoProfessor situacaoProfessor) {
        this.idSituacaoProfessor = situacaoProfessor.getIdSituacaoProfessor();
        this.situacaoProfessor = situacaoProfessor.getSituacaoProfessor();
        this.dataCadastro = situacaoProfessor.getDataCadastro();
        this.ativo = situacaoProfessor.getAtivo();
    }
}