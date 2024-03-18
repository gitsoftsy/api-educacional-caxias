package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_CATEGORIA_ESCOLA_PRIVADA")
@Data
public class CategoriaEscolaPrivada {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CATEGORIA_ESCOLA_PRIVADA")
	private Long idCategoriaEscolaPrivada;
	
	@Column(name = "CATEGORIA_ESCOLA_PRIVADA", nullable = false, unique = true)
	private String categoriaEscolaPrivada;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	
}
