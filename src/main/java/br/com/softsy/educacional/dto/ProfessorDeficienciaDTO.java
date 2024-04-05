package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.ProfessorDeficiencia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessorDeficienciaDTO {

    private Long idProfessorDeficiencia;
    private Long professorId;
    private DeficienciaDTO deficiencia;
    private LocalDateTime dataCadastro;

    public ProfessorDeficienciaDTO(ProfessorDeficiencia professorDeficiencia) {
        this.idProfessorDeficiencia = professorDeficiencia.getIdProfessorDeficiencia();
        this.professorId = professorDeficiencia.getProfessor().getIdProfessor();
        this.deficiencia = new DeficienciaDTO(professorDeficiencia.getDeficiencia());
        this.dataCadastro = professorDeficiencia.getDataCadastro();
    }
}