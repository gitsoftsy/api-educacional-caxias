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
@Table(name = "TBL_FORMA_OCUPACAO_PREDIO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_FORMA_OCUPACAO_PREDIO", columnNames = { "FORMA_OCUPACAO_PREDIO", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class FormaOcupacaoPredio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_FORMA_OCUPACAO_PREDIO")
	private Long idFormaOcupacaoPredio;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "FORMA_OCUPACAO_PREDIO", nullable = false, unique = true)
	private String formaOcupacaoPredio;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
