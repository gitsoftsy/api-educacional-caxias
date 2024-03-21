package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.CargoProfessor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CargoProfessorDTO {

    private Long idCargoProfessor;

    @NotNull
    private String cargoProfessor;

    private LocalDateTime dataCadastro;

    private Character ativo;

    public CargoProfessorDTO(CargoProfessor cargoProfessor) {
        this.idCargoProfessor = cargoProfessor.getIdCargoProfessor();
        this.cargoProfessor = cargoProfessor.getCargoProfessor();
        this.dataCadastro = cargoProfessor.getDataCadastro();
        this.ativo = cargoProfessor.getAtivo();
    }
}