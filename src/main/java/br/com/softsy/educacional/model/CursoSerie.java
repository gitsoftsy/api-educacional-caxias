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
@Table(name = "TBL_CURSO_SERIE", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_CURSO_SERIE", columnNames = { "SERIE", "ID_CURSO" })
		})
@Data
public class CursoSerie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CURSO_SERIE")
	private Long idCursoSerie;
	
	@ManyToOne
	@JoinColumn(name = "ID_CURSO", nullable = false)
	private Curso curso;
	
	@Column(name = "SERIE", nullable = false)
	private Integer serie;
	
	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
