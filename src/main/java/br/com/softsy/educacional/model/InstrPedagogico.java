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
@Table(name = "TBL_INSTR_PEDAGOGICO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_INSTR_PEDAGOGICO", columnNames = { "INSTR_PEDAGOGICO", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class InstrPedagogico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_INSTR_PEDAGOGICO")
	private Long idInstrPedagogica;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "INSTR_PEDAGOGICO", nullable = false, unique = true)
	private String instrPedagogico;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
}
