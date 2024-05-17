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
@Table(name = "TBL_ESCOLA_DISPOSITIVO")
@Data
public class EscolaDispositivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_DISPOSITIVO")
	private Long idEscolaDispositivo;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false, unique = true)
	private Escola escola;
	
	@Column(name = "QTD_COMPUTADORES_ALUNOS", nullable = false, length = 11)
	private Integer qtdComputadoresAlunos;
	
	@Column(name = "QTD_APARELHOS_DVD", nullable = false, length = 11)
	private Integer qtdAparelhosDvd;
	
	@Column(name = "QTD_IMPRESSORA", nullable = false, length = 11)
	private Integer qtdImpressora;
	
	@Column(name = "QTD_PARABOLICAS", nullable = false, length = 11)
	private Integer qtdParabolicas;
	
	@Column(name = "QTD_COPIADORAS", nullable = false, length = 11)
	private Integer qtdCopiadoras;
	
	@Column(name = "QTD_PROJETORES", nullable = false, length = 11)
	private Integer qtdProjetores;
	
	@Column(name = "QTD_TVS", nullable = false, length = 11)
	private Integer qtdTvs;
}
