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
@Table(name = "TBL_TURMA_COMPONENTES_CURRICULARES")
@Data
public class TurmaComponentesCurriculares {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURMA_COMPONENTES_CURRICULARES")
	private Long idTurmaComponentesCurriculares;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURMA", nullable = false)
	private Turma turma;
	
	@ManyToOne
	@JoinColumn(name = "ID_COMPONENTES_CURRICULARES", nullable = false)
	private ComponentesCurriculares componentesCurriculares;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	
}
