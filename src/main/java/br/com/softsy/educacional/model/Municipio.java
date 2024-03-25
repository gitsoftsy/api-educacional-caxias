package br.com.softsy.educacional.model;

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
@Table(name = "TBL_MUNICIPIO")
@Data
public class Municipio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MUNICIPIO")
	private Long idMunicipio;

	@Column(name = "COD_IBGE", nullable = false)
	private String codIbge;

	@Column(name = "NOME_MUNICIPIO", nullable = true)
	private String nomeMunicipio;

	@ManyToOne
	@JoinColumn(name = "ID_UF", nullable = true)
	private Uf uf;

}
