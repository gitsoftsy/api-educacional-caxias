package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_NIVEL_ESCOLARIDADE")
@Data
public class NivelEscolaridade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_NIVEL_ESCOLARIDADE")
	private Long idNivelEscolaridade;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "NIVEL_ESCOLARIDADE", nullable = true, unique = true)
	private String nivelEscolaridade;

	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
