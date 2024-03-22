package br.com.softsy.educacional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_PAIS")
@Data
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PAIS")
	private Long idPais;
	
	@Column(name = "COD_PAIS", nullable = false, unique = true)
	private String codPais;
	
	@Column(name = "NOME_PAIS", nullable = false, unique = true)
	private String nomePais;
	
	@Column(name = "CODIGO_ISO", nullable = false, unique = true)
	private String codigoIso;
	
}
