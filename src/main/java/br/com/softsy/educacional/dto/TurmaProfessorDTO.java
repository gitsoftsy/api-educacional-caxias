package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.TurmaProfessor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaProfessorDTO {

    private Long idTurmaProfessor;
    private TurmaDTO turma;
    private Long professorId;
    private LocalDateTime dataCadastro;
	private Character ativo;


    public TurmaProfessorDTO(TurmaProfessor turmaProfessor) {
        this.idTurmaProfessor = turmaProfessor.getIdTurmaProfessor();
        this.turma = new TurmaDTO(turmaProfessor.getTurma());
        this.professorId = turmaProfessor.getProfessor().getIdProfessor();
        this.dataCadastro = turmaProfessor.getDataCadastro();
        this.ativo = turmaProfessor.getAtivo();
    }
}
