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
@Table(name = "TBL_FORMA_ORGAN_ENSINO")
@Data
public class FormaOrganEnsino {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_FORMA_ORGAN_ENSINO")
	private Long idFormaOrganEnsino;

	@Column(name = "FORMA_ORGAN_ENSINO", nullable = true, unique = true)
	private String formaOrganEnsino;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

}
