package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

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
@Table(name = "TBL_ESCOLA_LINK_INTERNET")
@Data
public class EscolaLinkInternet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_LINK_INTERNET")
	private Long idEscolaLinkInternet;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROVEDOR_INTERNET", nullable = false)
	private ProvedorInternet provedorInternet;
	
	@Column(name = "VELOCIDADE_MB")
	private Double velocidadeMb;
	
	@Column(name = "ADMINISTRATIVO", nullable = false)
	private Character administrativo;
	
	@Column(name = "ESTUDANTE", nullable = false)
	private Character estudante;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
}
