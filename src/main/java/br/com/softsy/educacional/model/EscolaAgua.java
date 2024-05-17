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
@Table(name = "TBL_ESCOLA_AGUA")
@Data
public class EscolaAgua {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_AGUA")
	private Long idEscolaAgua;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false, unique = true)
	private Escola escola;
	
	@Column(name = "AGUA_TRATADA", nullable = false)
	private Character aguaTratada;
	
	@Column(name = "AGUA_POCO_ARTESIANO", nullable = false)
	private Character aguaPocoArtesiano;
	
	@Column(name = "AGUA_CACIMBA", nullable = false)
	private Character aguaCacimba;
	
	@Column(name = "AGUA_FONTE_RIO", nullable = false)
	private Character aguaFonteRio;
	
	@Column(name = "SEM_AGUA", nullable = false)
	private Character semAgua;
	
}
