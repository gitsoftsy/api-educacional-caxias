package br.com.softsy.educacional.dto;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ProfessorDisciplina;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessorDisciplinaDTO {
	
	private Long idProfessorDisciplina;
	@NotNull
	private Long professorId;
	@NotNull
	private Long disciplinaId;
	private LocalDateTime dataCadastro;
	private Character ativo;
	
	public ProfessorDisciplinaDTO(ProfessorDisciplina professorDisciplina) {
		this.idProfessorDisciplina = professorDisciplina.getIdProfessorDisciplina();
		this.professorId = professorDisciplina.getProfessor().getIdProfessor();
		this.disciplinaId = professorDisciplina.getDisciplina().getIdDisciplina();
		this.dataCadastro = professorDisciplina.getDataCadastro();
		this.ativo = professorDisciplina.getAtivo();
	}

}
