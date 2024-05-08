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
@Table(name = "TBL_DEPENDENCIA_ADIMINISTRATIVA")
@Data
public class DependenciaAdministrativa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DEPENDENCIA_ADIMINISTRATIVA")
	private Long idDependenciaAdministrativa;
	
	@Column(name = "DEPENDENCIA_ADIMINISTRATIVA", nullable = false, unique = true)
	private String dependenciaAdministrativa;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
