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
@Table(name = "TBL_DEFICIENCIA")
@Data
public class Deficiencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DEFICIENCIA")
	private Long idDeficiencia;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "DEFICIENCIA", nullable = true)
	private String deficiencia;

	@Column(name = "CID", nullable = true)
	private String cid;

	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
