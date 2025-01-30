package br.com.softsy.educacional.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "TBL_CURSO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_CURSO", columnNames = { "COD_CURSO", "ID_CONTA" }),
		@UniqueConstraint(name = "UQ_CURSO_INEP", columnNames = { "COD_CURSO_INEP", "ID_CONTA" })
		})
@Data
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CURSO")
	private Long idCurso;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;

	@Column(name = "COD_CURSO", nullable = false, unique = true, length = 10)
	private String codCurso;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "COD_CURSO_INEP", nullable = false, unique = true)
	private String codCursoInpe;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
	private Set<OfertaConcurso> ofertaConcurso = new HashSet<>();


	
}
