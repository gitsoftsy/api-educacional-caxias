package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_PROFESSOR_DISCIPLINA", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_PROFESSOR_DISCIPLINA", columnNames = { "ID_PROFESSOR", "ID_DISCIPLINA"})
		})
@Data
public class ProfessorDisciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROFESSOR_DISCIPLINA")
	private Long idProfessorDisciplina;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROFESSOR", nullable = false)
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "ID_DISCIPLINA", nullable = false)
	private Disciplina disciplina;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
