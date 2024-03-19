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
@Table(name = "TBL_ESCOLA_PROFISSIONAL")
@Data
public class EscolaProfissional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_PROFISSIONAL")
	private Long idEscolaProfissional;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_PROFISSIONAL", nullable = false)
	private TipoProfissional tipoProfissional;
	
	@Column(name = "QUANTIDADE", nullable = false)
	private Integer quantidade;
	
}
