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
@Table(name = "TBL_ESCOLA_TERMO_COLABORACAO")
@Data
public class EscolaTermoColaboracao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_TERMO_COLABORACAO")
	private Long idEscolaTermoColaboracao;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "DT_VALIDADE", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataValidade;
	
	@Column(name = "COORDENADOR", nullable = false)
	private String coordenador;
	
	@Column(name = "TERMO_COLABORACAO", nullable = false)
	private String termoColaboracao;
	
	@Column(name = "ANEXO", nullable = false)
	private String anexo;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = true)
	private Escola escola;

}
