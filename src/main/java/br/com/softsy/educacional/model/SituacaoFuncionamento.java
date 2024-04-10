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
@Table(name = "TBL_SITUACAO_FUNCIONAMENTO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_SITUACAO_FUNCIONAMENTO", columnNames = { "SITUACAO_FUNCIONAMENTO", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class SituacaoFuncionamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SITUACAO_FUNCIONAMENTO")
	private Long idSituacaoFuncionamento;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "SITUACAO_FUNCIONAMENTO", nullable = false, unique = true)
	private String situacaoFuncionamento;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	
}
