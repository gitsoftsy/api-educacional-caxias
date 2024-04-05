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
@Table(name = "TBL_PROFESSOR_DEFICIENCIA")
@Data
public class ProfessorDeficiencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROFESSOR_DEFICIENCIA")
	private Long idProfessorDeficiencia;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROFESSOR", nullable = false)
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEFICIENCIA", nullable = false)
	private Deficiencia deficiencia;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

}
