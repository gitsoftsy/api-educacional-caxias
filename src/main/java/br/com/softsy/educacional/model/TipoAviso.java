package br.com.softsy.educacional.model;

import java.util.Set;

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
@Table(name = "TBL_TIPO_AVISO")
@Data
public class TipoAviso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TIPO_AVISO")
	private Long idTipoAviso;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA")
	private Conta conta;
	
	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;

}
