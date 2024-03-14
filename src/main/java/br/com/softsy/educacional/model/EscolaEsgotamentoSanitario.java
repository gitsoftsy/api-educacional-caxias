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
@Table(name = "TBL_ESCOLA_ESGOTAMENTO_SANITARIO")
@Data
public class EscolaEsgotamentoSanitario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_ESGOTAMENTO_SANITARIO")
	private Long idEscolaEsgotamentoSanitario;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESGOTAMENTO_SANITARIO", nullable = false)
	private EsgotamentoSanitario esgotamentoSanitario;
}
