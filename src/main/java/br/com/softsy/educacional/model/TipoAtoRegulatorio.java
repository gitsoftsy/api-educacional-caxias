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
@Table(name = "TBL_TIPO_ATO_REGULATORIO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_TIPO_ATO_REGULATORIO", columnNames = { "TIPO_ATO_REGULATORIO", "ID_CONTA" })
		})
@Data
public class TipoAtoRegulatorio {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TIPO_ATO_REGULATORIO")
	private Long idTipoAtoRegulatorio;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "TIPO_ATO_REGULATORIO", nullable = false, unique = true)
	private String tipoAtoRegulatorio;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
