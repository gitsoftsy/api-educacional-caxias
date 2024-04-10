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
@Table(name = "TBL_TIPO_ENSINO_MEDIO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_TIPO_ENSINO_MEDIO", columnNames = { "TIPO_ENSINO_MEDIO", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class TipoEnsinoMedio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TIPO_ENSINO_MEDIO")
	private Long idTipoEnsinoMedio;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;

	@Column(name = "TIPO_ENSINO_MEDIO", nullable = true, unique = true)
	private String tipoEnsinoMedio;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
