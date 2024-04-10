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
@Table(name = "TBL_EQUIPAMENTO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_EQUIPAMENTO", columnNames = { "EQUIPAMENTO", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class Equipamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EQUIPAMENTO")
	private Long idEquipamento;
	
	@Column(name = "EQUIPAMENTO", nullable = false, unique = true)
	private String equipamento;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@ManyToOne
	@JoinColumn(name = "ID_MARCA_EQUIPAMENTO", nullable = false)
	private MarcaEquipamento marcaEquipamento;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
