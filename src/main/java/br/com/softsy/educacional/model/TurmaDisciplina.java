package br.com.softsy.educacional.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Table(name = "TBL_TURMA_DISCIPLINA", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_TURMA_DISCIPLINA", columnNames = { "SERIE", "ID_TURMA", "ID_DISCIPLINA", "ID_PERIODO_LETIVO"})
		})
@Data
public class TurmaDisciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURMA_DISCIPLINA")
	private Long idTurmaDisciplina;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURMA", nullable = false)
	private Turma turma;
	
	@ManyToOne
	@JoinColumn(name = "ID_DISCIPLINA", nullable = false)
	private Disciplina disciplina;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERIODO_LETIVO", nullable = false)
	private PeriodoLetivo periodoLetivo;
	
	@Column(name = "SERIE", nullable = false)
	private Long serie;
	
	@ManyToOne
	@JoinColumn(name = "ID_CURSO")
	private Curso curso;
	
	@ManyToOne
	@JoinColumn(name = "ID_CURRICULO")
	private Curriculo curriculo;
	
	@Column(name = "QTD_AULAS_PREVISTAS", nullable = false)
	private Integer qtdAulasPrevistas;
	
	@Column(name = "DT_INICIO")
	private LocalDate dtInicio;
	
	@Column(name = "DT_FIM")
	private LocalDate dtFim;
	
	@Column(name = "SITUACAO")
	private String situacao;
	
	@Column(name = "NIVEL_PRESENCA")
	private String nivelPresenca;
	
	@Column(name = "DURACAO_AULA")
	private LocalTime duracaoAula;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
