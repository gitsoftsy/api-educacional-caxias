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
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_TURNO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_TURNO_TURNO", columnNames = { "TURNO", "ID_CONTA"}),
		@UniqueConstraint(name = "UQ_TURNO_MNEMONICO", columnNames = {"ID_CONTA", "MNEMONICO"})
		})
@Data
public class Turno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURNO")
	private Long idTurno;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;
	
	@Column(name = "MNEMONICO", nullable = false, unique = true, length = 2)
	private String mnemonico;
	
	@Column(name = "TURNO", nullable = false, unique = true)
	private String turno;
	
	@Column(name = "HORA_INICIO")
	private Time horaInicio;
	
	@Column(name = "HORA_FIM")
	private Time horaFim;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
