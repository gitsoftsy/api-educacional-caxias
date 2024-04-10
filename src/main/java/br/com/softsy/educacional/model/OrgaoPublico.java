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
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_ORGAO_PUBLICO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_ORGAO_PUBLICO", columnNames = { "SIGLA", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class OrgaoPublico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ORGAO_PUBLICO")
	private Long idOrgaoPublico;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "SIGLA", nullable = false, unique = true)
	private String sigla;
	
	@Column(name = "ORGAO_PUBLICO", nullable = false, unique = true)
	private String orgaoPublico;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;


}
