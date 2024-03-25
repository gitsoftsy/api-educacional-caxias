package br.com.softsy.educacional.model;

import java.sql.Time;
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
@Table(name = "TBL_TURMA_DIA_SEMANA")
@Data
public class TurmaDiaSemana {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURMA_DIA_SEMANA")
	private Long idTurmaDiaSemana;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURMA", nullable = false)
	private Turma turma;
	
	@Column(name = "DIA_SEMANA", nullable = false)
	private Integer diaSemana;
	
	@Column(name = "HORA_INICIO", nullable = false)
	private Time horaInicio;
	
	@Column(name = "HORA_FIM", nullable = false)
	private Time horaFim;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

}
