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
@Table(name = "TBL_DISCIPLINA", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_DISCIPLINA", columnNames = { "COD_DISCIP", "ID_CONTA" }) })
@Data
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DISCIPLINA")
	private Long idDisciplina;

	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;

	@ManyToOne
	@JoinColumn(name = "ID_AREA_CONHECIMENTO")
	private AreaConhecimento areaConhecimento;

	@Column(name = "COD_DISCIP", nullable = false, unique = true, length = 15)
	private String codDiscip;

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

	@Column(name = "HORAS_LAB")
	private Double horasLab;
	
	@Column(name = "HORAS_ANO")
	private Double horasAno;
	
	@Column(name = "HORAS_SEMANAL")
	private Double horasSemanal;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
