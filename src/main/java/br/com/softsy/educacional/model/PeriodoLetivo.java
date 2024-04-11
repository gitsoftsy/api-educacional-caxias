package br.com.softsy.educacional.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_PERIODO_LETIVO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_PERIODO_LETIVO", columnNames = { "ANO", "ID_DEPENDENCIA_ADMINISTRATIVA", "PERIODO", "DESCRICAO", "TIPO_PERIODICIDADE" })
		})
@Data
public class PeriodoLetivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERIODO_LETIVO")
	private Long idPeriodoLetivo;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "ANO", nullable = false, length = 11)
	private Integer ano;
	
	@Column(name = "PERIODO", nullable = false, length = 11)
	private Integer periodo;
	
	@Column(name = "DT_INICIO")
	private LocalDate dtInicio;
	
	@Column(name = "DT_FIM")
	private LocalDate dtFim;
	
	@Column(name = "DESCRICAO", nullable = false, unique = true)
	private String descricao;
	
	@Column(name = "TIPO_PERIODICIDADE")
	private Character tipoPeriodicidade;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
