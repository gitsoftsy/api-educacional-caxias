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
@Table(name = "TBL_PROFESSOR_ESCOLA")
@Data
public class ProfessorEscola {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROFESSOR_ESCOLA")
	private Long idProfessorEscola;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROFESSOR", nullable = false)
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = false)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURNO_PROFESSOR", nullable = false)
	private TurnoProfessor turnoProfessor;
	
	@ManyToOne
	@JoinColumn(name = "ID_CARGO_PROFESSOR", nullable = false)
	private CargoProfessor cargoProfessor;
	
	@Column(name = "DT_NOMENCLATURA", nullable = false)
	private Long dtNomenclatura;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
}
