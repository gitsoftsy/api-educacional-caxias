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
@Table(name = "TBL_ESCOLA_LIC_SANITARIO")
@Data
public class EscolaLicSanitario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_LIC_SANITARIO")
	private Long idEscolaLicSanitario;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@Column(name = "LIC_SANITARIO", nullable = false)
	private String licSanitario;
	
	@Column(name = "DT_EMISSAO", nullable = false)
	private LocalDateTime dataEmissao;
	
	@Column(name = "DT_VALIDADE", nullable = false)
	private LocalDateTime dataValidade;
	
	@Column(name = "ANEXO")
	private String anexo;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
}

