package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Disciplina;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisciplinaDTO {
	private Long idDisciplina;

	private DependenciaAdministrativaDTO dependenciaAdmId;

	private Long escolaId;

	private String disciplina;
	private String nome;

	private Double creditos;

	private Double horasAula;

	private Double horasEstagio;

	private Double horasAtiv;

	private Double horasLab;

	private LocalDateTime dataCadastro;
	private Character ativo;

	public DisciplinaDTO(Disciplina disciplina) {
		this.idDisciplina = disciplina.getIdDisciplina();
		this.dependenciaAdmId = new DependenciaAdministrativaDTO(disciplina.getDependenciaAdm());
		this.escolaId = disciplina.getEscola().getIdEscola();
		this.disciplina = disciplina.getDisciplina();
		this.nome = disciplina.getNome();
		this.creditos = disciplina.getCreditos();
		this.horasAula = disciplina.getHorasAula();
		this.horasEstagio = disciplina.getHorasEstagio();
		this.horasAtiv = disciplina.getHorasAtiv();
		this.horasLab = disciplina.getHorasLab();
		this.dataCadastro = disciplina.getDataCadastro();
		this.ativo = disciplina.getAtivo();
	}
}
