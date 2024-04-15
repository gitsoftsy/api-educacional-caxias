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
@Table(name = "TBL_GRADE_CURRICULAR", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_GRADE_CURRICULAR", columnNames = { "ID_CURSO_SERIE", "ID_TURNO", "ID_DISCIPLINA", "ID_CURRICULO"})
		})
@Data
public class GradeCurricular {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_GRADE_CURRICULAR")
	private Long idGradeCurricular;
	
	@ManyToOne
	@JoinColumn(name = "ID_CURSO_SERIE", nullable = false)
	private CursoSerie cursoSerie;

	@ManyToOne
	@JoinColumn(name = "ID_TURNO", nullable = false)
	private Turno turno;
	
	@ManyToOne
	@JoinColumn(name = "ID_DISCIPLINA", nullable = false)
	private Disciplina disciplina;
	
	@ManyToOne
	@JoinColumn(name = "ID_CURRICULO", nullable = false)
	private Curriculo curriculo;
	
	@Column(name = "OBRIGATORIA", nullable = false)
	private Character obrigatoria;
	
	@Column(name = "RETEM_SERIE", nullable = false)
	private Character retemSerie;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
