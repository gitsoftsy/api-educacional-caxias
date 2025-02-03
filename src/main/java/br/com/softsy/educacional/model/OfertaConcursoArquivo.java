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
@Table(name = "TBL_OFERTA_CONCURSO_ARQ")
@Data
public class OfertaConcursoArquivo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_OFERTA_CONCURSO_ARQ")
	private Long idOfertaConcursoArq;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA")
	private Conta conta;

	@ManyToOne
	@JoinColumn(name = "ID_OFERTA_CONCURSO")
	private OfertaConcurso ofertaConcurso;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "PATH_ARQ", length = 2000)
	private String pathArq;
	
	@Column(name = "NOME_ARQ", length = 2000)
	private String  nomeArq;
	
	@Column(name = "ORDEM", nullable = false)
	private Integer ordem;

	

}
