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
    private Long turmaDisciplinaId;
    @NotNull
    private Long areaConhecimentoId;
    private LocalDateTime dataCadastro;

    public CadastroTurmaAreaConhecimentoDTO(TurmaAreaConhecimento turmaAreaConhecimento) {
        this.idTurmaAreaConhecimento = turmaAreaConhecimento.getIdTurmaAreaConhecimento();
        this.turmaDisciplinaId = turmaAreaConhecimento.getTurmaDisciplina().getIdTurmaDisciplina();
        this.areaConhecimentoId = turmaAreaConhecimento.getAreaConhecimento().getIdAreaConhecimento();
        this.dataCadastro = turmaAreaConhecimento.getDataCadastro();
    }
}
