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
@Table(name = "TBL_DISCIPLINA")
@Data
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DISCIPLINA")
	private Long idDisciplina;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA")
	private Escola escola;
	
	@Column(name = "DISCIPLINA", nullable = false, unique = true, length = 15)
	private String disciplina;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "CREDITOS")
	private Double creditos;
	
	@Column(name = "HORAS_AULA")
	private Double horasAula;
	
	@Column(name = "HORAS_ESTAGIO")
	private Double horasEstagio;
	
	@Column(name = "HORAS_ATIV")
	private Double horasAtiv;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
