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
@Table(name = "TBL_MODALIDADE_ESCOLA", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_MODALIDADE_ESCOLA", columnNames = { "MODALIDADE_ESCOLA", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class ModalidadeEscola {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MODALIDADE_ESCOLA")
	private Long idModalidadeEscola;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "MODALIDADE_ESCOLA", nullable = false, unique = true)
	private String modalidadeEscola;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
