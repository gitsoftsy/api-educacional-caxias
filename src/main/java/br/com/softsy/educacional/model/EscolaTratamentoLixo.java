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
@Table(name = "TBL_ESCOLA_TRATAMENTO_LIXO")
@Data
public class EscolaTratamentoLixo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_TRATAMENTO_LIXO")
	private Long idEscolaTratamentoLixo;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_TRATAMENTO_LIXO", nullable = false)
	private TratamentoLixo tratamentoLixo;
}
