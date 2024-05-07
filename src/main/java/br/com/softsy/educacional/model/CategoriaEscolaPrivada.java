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
@Table(name = "TBL_CATEGORIA_ESCOLA_PRIVADA", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_CATEGORIA_ESCOLA_PRIVADA", columnNames = { "CATEGORIA_ESCOLA_PRIVADA", "ID_CONTA" })
		})
@Data
public class CategoriaEscolaPrivada {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CATEGORIA_ESCOLA_PRIVADA")
	private Long idCategoriaEscolaPrivada;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "CATEGORIA_ESCOLA_PRIVADA", nullable = false, unique = true)
	private String categoriaEscolaPrivada;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	
}
