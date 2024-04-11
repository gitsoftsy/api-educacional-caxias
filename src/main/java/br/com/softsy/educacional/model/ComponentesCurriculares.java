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
@Table(name = "TBL_COMPONENTES_CURRICULARES")
@Data
public class ComponentesCurriculares {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_COMPONENTES_CURRICULARES")
	private Long idComponentesCurriculares;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "COMPONENTES_CURRICULARES", nullable = false, unique = true)
	private String componentesCurriculares;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

}
