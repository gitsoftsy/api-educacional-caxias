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
@Table(name = "TBL_TURMA_AREA_CONHECIMENTO")
@Data
public class TurmaAreaConhecimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURMA_AREA_CONHECIMENTO")
	private Long idTurmaAreaConhecimento;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURMA_DISCIPLINA", nullable = false)
	private TurmaDisciplina turmaDisciplina;
	
	@ManyToOne
	@JoinColumn(name = "ID_AREA_CONHECIMENTO", nullable = false)
	private AreaConhecimento areaConhecimento;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

}
