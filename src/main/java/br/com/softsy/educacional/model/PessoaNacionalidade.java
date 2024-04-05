package br.com.softsy.educacional.model;

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
@Table(name = "TBL_PESSOA_NACIONALIDADE")
@Data
public class PessoaNacionalidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PESSOA_NACIONALIDADE")
	private Long idPessoaNacionalidade;
	
	@ManyToOne
	@JoinColumn(name = "ID_PESSOA", nullable = false)
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "ID_NACIONALIDADE", nullable = false)
	private Nacionalidade nacionalidade;
	
	
}
