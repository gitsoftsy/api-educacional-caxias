package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_CURSO_IMG")
@Data
public class CursoImg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CURSO_IMG")
	private Long idCursoImg;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA")
	private Conta conta;

	@ManyToOne
	@JoinColumn(name = "ID_CURSO")
	private Curso curso;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ORDEM", nullable = false)
	private Integer ordem;
	
	@Column(name = "PATH_IMG", length = 2000)
	private String pathImg;
	
	@Column(name = "TIPO_DISPOSITIVO", nullable = false,  length = 1)
	private String  tipoDispositivo;
	
	@Column(name = "URL", nullable = false, length = 300)
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
	
	
	
	
}

