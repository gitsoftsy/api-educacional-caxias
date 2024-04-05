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

import lombok.Data;

@Entity
@Table(name = "TBL_TURMA_PROFESSOR")
@Data
public class TurmaProfessor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURMA_PROFESSOR")
	private Long idTurmaProfessor;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURMA", nullable = false)
	private Turma turma;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROFESSOR", nullable = false)
	private Professor professor;
	
	@Column(name = "TIPO_PROFESSOR", length = 1)
	private Character tipoProfessor;
	
	@Column(name = "TIPO_VAGA", length = 1)
	private Character tipoVaga;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;


}
