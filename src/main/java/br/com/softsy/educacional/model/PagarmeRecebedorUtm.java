package br.com.softsy.educacional.model;

import java.math.BigDecimal;
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
@Table(name = "TBL_PAGARME_RECEBEDOR_UTM")
@Data
public class PagarmeRecebedorUtm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PAGARME_RECEBEDOR_UTM")
	private Long idPagarmeRecebedorUtm;

	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;

//    @ManyToOne
//    @JoinColumn(name = "ID_PAGARME_RECEBEDOR", nullable = false)
//    private Pagarme Pagarme;

	@ManyToOne
	@JoinColumn(name = "ID_UTM", nullable = false)
	private Utm utm;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@Column(name = "TIPO_REPASSE", nullable = false)
	private Character tipoRepasse;
	
	@Column(name = "VALOR_REPASSE", precision = 10, scale = 2)
    private BigDecimal valorRepasse;

}
