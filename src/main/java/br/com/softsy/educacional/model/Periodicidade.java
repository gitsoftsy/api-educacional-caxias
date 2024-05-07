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

import lombok.Data;

@Entity
@Table(name = "TBL_PERIODICIDADE", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_PERIODICIDADE", columnNames = { "PERIODICIDADE", "ID_CONTA" })
})
@Data
public class Periodicidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERIODICIDADE")
	private Long idPeriodicidade;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "PERIODICIDADE", nullable = false, unique = true)
	private String periodicidade;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@OneToMany(mappedBy = "periodicidade", cascade = CascadeType.ALL)
	private Set<EscolaRegimeEscolar> escolaRegime = new HashSet<>();
}
