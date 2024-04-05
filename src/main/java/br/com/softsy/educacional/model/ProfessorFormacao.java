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
@Table(name = "TBL_PROFESSOR_FORMACAO")
@Data
public class ProfessorFormacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROFESSOR_FORMACAO")
	private Long idProfessorFormacao;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROFESSOR", nullable = false)
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "ID_MODALIDADE_ESCOLA", nullable = false)
	private ModalidadeEscola modalidadeEscola;
	
	@Column(name = "NOME_CURSO", nullable = false)
	private String nomeCurso;
	
	@Column(name = "IES", nullable = false)
	private String ies;
	
	@Column(name = "ANO_CONCLUSAO", nullable = false)
	private Integer anoConclusao;
	
	@Column(name = "CERTIFICADO")
	private String certificado;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
}
