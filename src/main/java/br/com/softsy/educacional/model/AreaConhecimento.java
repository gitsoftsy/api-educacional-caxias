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
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_AREA_CONHECIMENTO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_AREA_CONHECIMENTO", columnNames = { "AREA_CONHECIMENTO", "ID_CONTA" })
		})
@Data
public class AreaConhecimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_AREA_CONHECIMENTO")
	private Long idAreaConhecimento;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "AREA_CONHECIMENTO", nullable = false, unique = true)
	private String areaConhecimento;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	
}
