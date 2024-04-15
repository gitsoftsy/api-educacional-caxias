package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.TurmaAreaConhecimento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaAreaConhecimentoDTO {

    private Long idTurmaAreaConhecimento;
    private Long turmaDisciplinaId;
    private AreaConhecimentoDTO areaConhecimento;
    private LocalDateTime dataCadastro;

    public TurmaAreaConhecimentoDTO(TurmaAreaConhecimento turmaAreaConhecimento) {
        this.idTurmaAreaConhecimento = turmaAreaConhecimento.getIdTurmaAreaConhecimento();
        this.turmaDisciplinaId = turmaAreaConhecimento.getTurmaDisciplina().getIdTurmaDisciplina();
        this.areaConhecimento = new AreaConhecimentoDTO(turmaAreaConhecimento.getAreaConhecimento());
        this.dataCadastro = turmaAreaConhecimento.getDataCadastro();
    }
}