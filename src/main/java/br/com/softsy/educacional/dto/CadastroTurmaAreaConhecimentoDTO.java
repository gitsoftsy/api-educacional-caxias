package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TurmaAreaConhecimento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroTurmaAreaConhecimentoDTO {

    private Long idTurmaAreaConhecimento;
    @NotNull
    private Long turmaId;
    @NotNull
    private Long areaConhecimentoId;
    private LocalDateTime dataCadastro;

    public CadastroTurmaAreaConhecimentoDTO(TurmaAreaConhecimento turmaAreaConhecimento) {
        this.idTurmaAreaConhecimento = turmaAreaConhecimento.getIdTurmaAreaConhecimento();
        this.turmaId = turmaAreaConhecimento.getTurma().getIdTurma();
        this.areaConhecimentoId = turmaAreaConhecimento.getAreaConhecimento().getIdAreaConhecimento();
        this.dataCadastro = turmaAreaConhecimento.getDataCadastro();
    }
}
