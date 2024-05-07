package br.com.softsy.educacional.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity
@Table(name = "TBL_TRATAMENTO_LIXO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_TRATAMENTO_LIXO", columnNames = { "TRATAMENTO_LIXO", "ID_CONTA" })
		})
@Data
public class TratamentoLixo {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TRATAMENTO_LIXO")
	private Long idTratamentoLixo;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "TRATAMENTO_LIXO", nullable = false, unique = true)
	private String tratamentoLixo;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tratamentoLixo", cascade = CascadeType.ALL)
	private Set<EscolaTratamentoLixo> EscolaTratamento = new HashSet<>();
}
