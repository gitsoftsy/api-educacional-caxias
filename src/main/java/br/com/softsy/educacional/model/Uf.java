package br.com.softsy.educacional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_UF")
@Data
public class Uf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_UF")
	private Long idUf;
	
	@Column(name = "COD_UF", length = 2, unique = true)
	private String codUf;
	
	@Column(name = "NOME_UF", nullable = false)
	private String nomeUf;
	
}
