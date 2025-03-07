package br.com.softsy.educacional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_APLICACAO")
@Data
public class Aplicacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_APLICACAO")
	private Long idAplicacao;

	@Column(name = "APLICACAO", length = 30, nullable = false)
	private String aplicacao;
}
