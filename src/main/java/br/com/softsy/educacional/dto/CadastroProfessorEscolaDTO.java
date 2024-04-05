package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.ProfessorEscola;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroProfessorEscolaDTO {

    private Long idProfessorEscola;
    private Long professorId;
    private Long escolaId;
    private Long turnoProfessorId;
    private Long cargoProfessorId;
    private Long dtNomenclatura;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public CadastroProfessorEscolaDTO(ProfessorEscola professorEscola) {
        this.idProfessorEscola = professorEscola.getIdProfessorEscola();
        this.professorId = professorEscola.getProfessor().getIdProfessor();
        this.escolaId = professorEscola.getEscola().getIdEscola();
        this.turnoProfessorId = professorEscola.getTurnoProfessor().getIdTurnoProfessor();
        this.cargoProfessorId = professorEscola.getCargoProfessor().getIdCargoProfessor();
        this.dtNomenclatura = professorEscola.getDtNomenclatura();
        this.dataCadastro = professorEscola.getDataCadastro();
        this.ativo = professorEscola.getAtivo();
    }
}