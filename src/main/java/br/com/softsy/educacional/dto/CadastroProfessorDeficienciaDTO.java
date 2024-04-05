package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ProfessorDeficiencia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroProfessorDeficienciaDTO {

    private Long idProfessorDeficiencia;
    @NotNull
    private Long professorId;
    @NotNull
    private Long deficienciaId;
    private LocalDateTime dataCadastro;

    public CadastroProfessorDeficienciaDTO(ProfessorDeficiencia professorDeficiencia) {
        this.idProfessorDeficiencia = professorDeficiencia.getIdProfessorDeficiencia();
        this.professorId = professorDeficiencia.getProfessor().getIdProfessor();
        this.deficienciaId = professorDeficiencia.getDeficiencia().getIdDeficiencia();
        this.dataCadastro = professorDeficiencia.getDataCadastro();
    }
}
