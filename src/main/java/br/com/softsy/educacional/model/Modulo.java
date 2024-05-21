package br.com.softsy.educacional.model;

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
@Table(name = "TBL_MODULO")
@Data
public class Modulo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MODULO")
	private Long idModulo;
	
	@Column(name = "MODULO", nullable = false, length = 50, unique = true)
	private String modulo;
	
	@Column(name = "ICONE")
	private String icone;
	
	@OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
	private Set<Transacao> transacao = new HashSet<>();
	
}