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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity
@Table(name = "TBL_TRATAMENTO_LIXO")
@Data
public class TratamentoLixo {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TRATAMENTO_LIXO")
	private Long idTratamentoLixo;
	
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
