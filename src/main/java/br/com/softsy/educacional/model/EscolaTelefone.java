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
@Table(name = "TBL_ESCOLA_TELEFONE")
@Data
public class EscolaTelefone {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TELEFONE_ESCOLA")
	private Long idTelefoneEscola;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_TELEFONE", nullable = false)
	private TipoTelefone tipoTelefone;
	
	@Column(name = "TELEFONE")
	private String telefone;

}
