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
@Table(name = "TBL_ESCOLA_REGIME_ESCOLAR")
@Data
public class EscolaRegimeEscolar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_REGIME_ESCOLAR")
	private Long idEscolaRegimeEscolar;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;
	
	@Column(name = "DT_HOMOLOGACAO", nullable = false)
	private LocalDateTime dataHomologacao;
	
	@Column(name = "DT_INICIO_VIGENCIA", nullable = false)
	private LocalDateTime dataInicioVigencia;
	
	@Column(name = "DT_FIM_VIGENCIA", nullable = false)
	private LocalDateTime dataFimVigencia;
	
	@Column(name = "ANO_CICLO", nullable = false)
	private Integer anoCiclo;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERIODICIDADE", nullable = false)
	private Periodicidade periodicidade;
	
	@Column(name = "ANEXO")
	private String anexo;
	
}
