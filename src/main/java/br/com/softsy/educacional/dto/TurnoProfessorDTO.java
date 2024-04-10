package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TurnoProfessor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurnoProfessorDTO {

	private Long idTurnoProfessor;
	
	@NotNull
	private Long dependenciaAdmId;

	private String turnoProfessor;

	private LocalDateTime dataCadastro;

	private Character ativo;

	public TurnoProfessorDTO(TurnoProfessor turnoProfessor) {
		this.idTurnoProfessor = turnoProfessor.getIdTurnoProfessor();
		this.turnoProfessor = turnoProfessor.getTurnoProfessor();
		this.dataCadastro = turnoProfessor.getDataCadastro();
		this.ativo = turnoProfessor.getAtivo();
		this.dependenciaAdmId = turnoProfessor.getDependenciaAdm().getIdDependenciaAdministrativa();
	}
}
