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
@Table(name = "TBL_TIPO_MATRICULA")
@Data
public class TipoMatricula {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TIPO_MATRICULA")
	private Long idTipoMatricula;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	 @Column(name = "TIPO_MATRICULA", length = 55, nullable = false)
	 private String tipoMatricula;
}
