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

import lombok.Data;

@Entity
@Table(name = "TBL_PERIODICIDADE")
@Data
public class Periodicidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERIODICIDADE")
	private Long idPeriodicidade;
	
	@Column(name = "PERIODICIDADE", nullable = false, unique = true)
	private String periodicidade;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@OneToMany(mappedBy = "periodicidade", cascade = CascadeType.ALL)
	private Set<EscolaRegimeEscolar> escolaRegime = new HashSet<>();
}
