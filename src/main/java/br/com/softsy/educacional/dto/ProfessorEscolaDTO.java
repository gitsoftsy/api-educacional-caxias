package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.ProfessorEscola;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessorEscolaDTO {

    private Long idProfessorEscola;
    private Long professorId;
    private Long escolaId;
    private TurnoProfessorDTO turnoProfessor;
    private CargoProfessorDTO cargoProfessor;
    private Long dtNomenclatura;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public ProfessorEscolaDTO(ProfessorEscola professorEscola) {
        this.idProfessorEscola = professorEscola.getIdProfessorEscola();
        this.professorId = professorEscola.getProfessor().getIdProfessor();
        this.escolaId = professorEscola.getEscola().getIdEscola();
        this.turnoProfessor = new TurnoProfessorDTO(professorEscola.getTurnoProfessor());
        this.cargoProfessor = new CargoProfessorDTO(professorEscola.getCargoProfessor());
        this.dtNomenclatura = professorEscola.getDtNomenclatura();
        this.dataCadastro = professorEscola.getDataCadastro();
        this.ativo = professorEscola.getAtivo();
    }
}
