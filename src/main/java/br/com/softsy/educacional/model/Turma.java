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
@Table(name = "TBL_TURMA",
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_TURMA", columnNames = {"ID_ESCOLA", "ID_PERIODO_LETIVO", "ID_TURNO", "ID_GRADE_CURRICULAR"}),
		@UniqueConstraint(name = "UQ_TURMA2", columnNames = {"ID_ESCOLA", "NOME_TURMA", "ID_GRADE_CURRICULAR"})
		})
@Data
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURMA")
	private Long idTurma;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = true)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERIODO_LETIVO", nullable = true)
	private PeriodoLetivo periodoLetivo;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURNO", nullable = true)
	private Turno turno;
	
	@Column(name = "NOME_TURMA", nullable = false)
	private String nomeTurma;
	
	@Column(name = "COD_TURMA_INEP", length = 50)
	private String codTurmaInep;
	
	@ManyToOne
	@JoinColumn(name = "ID_GRADE_CURRICULAR", nullable = true)
	private GradeCurricular gradeCurricular;
	
	@Column(name = "LIBRAS", nullable = false)
	private Character libras;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@Column(name = "VAGAS", nullable = false)
	private Integer vagas;
	
	@Column(name = "CONTROLA_VAGAS", nullable = false)
	private Character controlaVagas;
	
	
}
