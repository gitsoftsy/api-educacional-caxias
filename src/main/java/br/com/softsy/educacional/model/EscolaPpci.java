package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_ESCOLA_PPCI")
@Data
public class EscolaPpci {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_PPCI")
	private Long idEscolaPpci;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "DT_VALIDADE", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataValidade;

	@Column(name = "DT_EMISSAO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataEmissao;

	@Column(name = "PPCI", nullable = false)
	private String ppci;

	@Lob
	@Column(name = "ANEXO")
	private byte[] anexo;

	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = true)
	private Escola escola;

}
