package br.com.softsy.educacional.model;

import java.sql.Time;
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
@Table(name = "TBL_CANDIDATO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_CANDIDATO_PESSOA", columnNames = { "CANDIDADTO", "ID_CANDIDATO"}),
		})
@Data
public class Candidato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CANDIDATO")
	private Long idCandidato;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA",  nullable = false)
	private Conta conta;
	
	@ManyToOne
	@JoinColumn(name = "ID_PESSOA",  nullable = false)
	private Pessoa idPessoa;
	
	@Column(name = "CANDIDATO",  nullable = false)
	private String candidato;
	
	@ManyToOne
	@JoinColumn(name = "ID_OFERTA_CONCURSO")
	private OfertaConcurso idOfertaConcurso;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_INGRESSO",  nullable = false)
	private TipoIngresso idTipoIngresso;
	
	@Column(name = "CLASSIFICACAO")
	private String classificacao;
	
	@ManyToOne
	@JoinColumn(name = "ID_ALUNO")
	private String idAluno;
	
	@Column(name = "APROVADO")
	private String aprovado;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO_APROVADO")
	private Usuario  idUsuarioAprovado;
	

}
