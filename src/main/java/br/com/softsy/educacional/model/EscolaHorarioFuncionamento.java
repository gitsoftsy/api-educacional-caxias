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
@Table(name = "TBL_ESCOLA_HORARIO_FUNCIONAMENTO")
@Data
public class EscolaHorarioFuncionamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA_HORARIO_FUNCIONAMENTO")
	private Long idEscolaHorarioFuncionamento;
	
	@Column(name = "DESCRICAO", length = 55)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@Column(name = "DIA_SEMANA", nullable = false)
	private Integer diaSemana;
	
	@Column(name = "HORA_INCIO", nullable = false)
	private Time horaInicio;
	
	@Column(name = "HORA_FIM", nullable = false)
	private Time horaFim;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
