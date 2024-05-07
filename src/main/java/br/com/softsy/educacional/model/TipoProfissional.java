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
@Table(name = "TBL_TIPO_PROFISSIONAL", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_TIPO_PROFISSIONAL", columnNames = { "TIPO_PROFISSIONAL", "ID_CONTA" })
		})
@Data
public class TipoProfissional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TIPO_PROFISSIONAL")
	private Long idTipoProfissional;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "TIPO_PROFISSIONAL", nullable = false, unique = true)
	private String tipoProfissional;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tipoProfissional", cascade = CascadeType.ALL)
	private Set<EscolaProfissional> profissional = new HashSet<>();
	
}
