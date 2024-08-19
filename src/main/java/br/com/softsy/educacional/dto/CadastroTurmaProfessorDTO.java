package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.TurmaProfessor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroTurmaProfessorDTO {

    private Long idTurmaProfessor;
    private Long turmaId;
    private Long professorId;
    private LocalDateTime dataCadastro;
	private Character ativo;

    public CadastroTurmaProfessorDTO(TurmaProfessor turmaProfessor) {
        this.idTurmaProfessor = turmaProfessor.getIdTurmaProfessor();
        this.turmaId = turmaProfessor.getTurma().getIdTurma();
        this.professorId = turmaProfessor.getProfessor().getIdProfessor();
        this.dataCadastro = turmaProfessor.getDataCadastro();
        this.ativo = turmaProfessor.getAtivo();
    }
}
