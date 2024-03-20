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
@Table(name = "TBL_ESCOLA_DEPENDENCIA")
@Data
public class EscolaDependencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_DEPENDENCIA")
	private Long idEscolaDependencia;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@Column(name = "DEPENDENCIA", nullable = false)
	private String dependencia;
	
	@Column(name = "ACESSIVEL")
	private Character acessivel;
	
	@Column(name = "INTERNA_EXTERNA")
	private Character internaExterna;
	
	@Column(name = "CLIMATIZADA")
	private Character climatizada;
	
	@Column(name = "QUANTIDADE", nullable = false)
	private Integer quantidade;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_DEPENDENCIA", nullable = false)
	private TipoDependencia tipoDependencia;

}
