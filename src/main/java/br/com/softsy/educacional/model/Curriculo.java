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
@Table(name = "TBL_CURRICULO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_CURRICULO", columnNames = { "CURRICULO", "ID_CURSO", "ID_CONTA" })
		})
@Data
public class Curriculo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CURRICULO")
	private Long idCurriculo;
	
	@ManyToOne
	@JoinColumn(name = "ID_CURSO", nullable = false)
	private Curso curso;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "CURRICULO", nullable = false)
	private String curriculo;
	
	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;
	
	@Column(name = "DT_HOMOLOGACAO")
	private LocalDateTime dtHomologacao;
	
	@Column(name = "DT_EXTINCAO")
	private LocalDateTime dtExtincao;
	
	@Column(name = "PRAZO_IDEAL")
	private Integer prazoIdeal;
	
	@Column(name = "PRAZO_MAX")
	private Integer prazoMax;
	
	@Column(name = "CREDITOS")
	private Double creditos;
	
	@Column(name = "AULAS_PREVISTAS")
	private Integer aulasPrevistas;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
