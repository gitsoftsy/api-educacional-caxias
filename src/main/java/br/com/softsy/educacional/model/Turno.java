package br.com.softsy.educacional.model;

import java.sql.Time;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_TURNO")
@Data
public class Turno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURNO")
	private Long idTurno;
	
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

}
