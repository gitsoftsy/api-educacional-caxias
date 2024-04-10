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
@Table(name = "TBL_ENTIDADE_SUPERIOR", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_ENTIDADE_SUPERIOR", columnNames = { "ENTIDADE_SUPERIOR", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class EntidadeSuperior {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ENTIDADE_SUPERIOR")
	private Long idEntidadeSuperior;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "ENTIDADE_SUPERIOR", nullable = false, unique = true)
	private String entidadeSuperior;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
