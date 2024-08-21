package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.softsy.educacional.model.SituacaoAluno;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SituacaoAlunoDTO {

    private Long idSituacaoAluno;
    private LocalDateTime dataCadastro;
    private String situacaoAluno;
    private Long contaId;

    public SituacaoAlunoDTO(SituacaoAluno situacaoAluno) {
        this.idSituacaoAluno = situacaoAluno.getIdSituacaoAluno();
        this.dataCadastro = situacaoAluno.getDataCadastro();
        this.situacaoAluno = situacaoAluno.getSituacaoAluno();
        this.contaId = situacaoAluno.getConta().getIdConta();
    }
}