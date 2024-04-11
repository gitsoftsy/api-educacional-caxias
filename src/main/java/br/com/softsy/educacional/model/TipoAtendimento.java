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

import lombok.Data;

@Entity
@Table(name = "TBL_TIPO_ATENDIMENTO")
@Data
public class TipoAtendimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TIPO_ATENDIMENTO")
	private Long idTipoAtendimento;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "TIPO_ATENDIMENTO", nullable = false, unique = true)
	private String tipoAtendimento;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

}
