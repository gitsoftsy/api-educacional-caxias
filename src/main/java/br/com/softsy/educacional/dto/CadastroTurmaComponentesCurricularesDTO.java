package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TurmaComponentesCurriculares;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroTurmaComponentesCurricularesDTO {

    private Long idTurmaComponentesCurriculares;
    @NotNull
    private Long turmaDisciplinaId;
    @NotNull
    private Long componentesCurricularesId;

    public CadastroTurmaComponentesCurricularesDTO(TurmaComponentesCurriculares turmaComponentesCurriculares) {
        this.idTurmaComponentesCurriculares = turmaComponentesCurriculares.getIdTurmaComponentesCurriculares();
        this.turmaDisciplinaId = turmaComponentesCurriculares.getTurmaDisciplina().getIdTurmaDisciplina();
        this.componentesCurricularesId = turmaComponentesCurriculares.getComponentesCurriculares().getIdComponentesCurriculares();
    }
}