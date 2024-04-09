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
@Table(name = "TBL_MARCA_EQUIPAMENTO", 
	uniqueConstraints = { 
			@UniqueConstraint(name = "UQ_MARCA_EQUIPAMENTO", columnNames = { "MARCA_EQUIPAMENTO", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class MarcaEquipamento {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MARCA_EQUIPAMENTO")
	private Long idMarcaEquipamento;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "MARCA_EQUIPAMENTO", nullable = false, unique = true)
	private String marcaEquipamento;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
