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
@Table(name = "TBL_ESCOLA_FONTE_ENERGIA_ELETRICA")
@Data
public class EscolaFonteEnergiaEletrica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_FONTE_ENERGIA_ELETRICA")
	private Long idEscolaFonteEnergiaEletrica;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_FONTE_ENERGIA_ELETRICA", nullable = false)
	private FonteEnergiaEletrica fonteEnergiaEletrica;
}
