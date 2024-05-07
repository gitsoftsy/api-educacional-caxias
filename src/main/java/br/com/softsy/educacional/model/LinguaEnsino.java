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
@Table(name = "TBL_LINGUA_ENSINO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_LINGUA_ENSINO", columnNames = { "LINGUA_ENSINO", "ID_CONTA" })
		})
@Data
public class LinguaEnsino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_LINGUA_ENSINO")
	private Long idLinguaEnsino;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "LINGUA_ENSINO", nullable = false, unique = true)
	private String linguaEnsino;
	
	@Column(name = "LINGUA_INDIGENA", nullable = false, unique = false)
	private String linguaIndigena;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
