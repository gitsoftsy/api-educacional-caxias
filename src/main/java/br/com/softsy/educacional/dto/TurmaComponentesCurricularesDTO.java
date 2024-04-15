package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.TurmaComponentesCurriculares;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaComponentesCurricularesDTO {

    private Long idTurmaComponentesCurriculares;
    private Long turmaDisciplinaId;
    private ComponentesCurricularesDTO componentesCurriculares;
    private LocalDateTime dataCadastro;

    public TurmaComponentesCurricularesDTO(TurmaComponentesCurriculares turmaComponentesCurriculares) {
        this.idTurmaComponentesCurriculares = turmaComponentesCurriculares.getIdTurmaComponentesCurriculares();
        this.turmaDisciplinaId = turmaComponentesCurriculares.getTurmaDisciplina().getIdTurmaDisciplina();
        this.componentesCurriculares = new ComponentesCurricularesDTO(turmaComponentesCurriculares.getComponentesCurriculares());
        this.dataCadastro = turmaComponentesCurriculares.getDataCadastro();
    }
}