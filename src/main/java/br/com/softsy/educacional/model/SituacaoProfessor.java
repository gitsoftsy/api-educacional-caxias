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
@Table(name = "TBL_SITUACAO_PROFESSOR", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_SITUACAO_PROFESSOR", columnNames = { "SITUACAO_PROFESSOR", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class SituacaoProfessor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SITUACAO_PROFESSOR")
	private Long idSituacaoProfessor;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "SITUACAO_PROFESSOR", nullable = false, unique = true)
	private String situacaoProfessor;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
}
