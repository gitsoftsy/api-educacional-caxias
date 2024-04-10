package br.com.softsy.educacional.model;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_PROFESSOR", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_PROFESSOR_INEP", columnNames = { "CODIGO_INEP", "ID_DEPENDENCIA_ADMINISTRATIVA" }),
		@UniqueConstraint(name = "UQ_PROFESSOR_MATRICULA", columnNames = {"MATRICULA", "ID_DEPENDENCIA_ADMINISTRATIVA"})
		})
@Data
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROFESSOR")
	private Long idProfessor;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@ManyToOne
	@JoinColumn(name = "ID_PESSOA", nullable = false)
	private Pessoa pessoa;
	
	@Column(name = "CODIGO_INEP", nullable = false, unique = true)
	private String codigoInep;
	
	@Column(name = "MATRICULA", nullable = false, unique = true)
	private String matricula;
	
	@ManyToOne
	@JoinColumn(name = "ID_SITUACAO_PROFESSOR", nullable = false)
	private SituacaoProfessor situacaoProfessor;
	
	@Column(name = "DEFICIENTE", nullable = false)
	private Character deficiente;
	
	@Column(name = "AUTISTA", nullable = false)
	private Character autista;
	
	@Column(name = "ALTAS_HABILIDADES", nullable = false)
	private Character altasHabilidades;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@ManyToOne
	@JoinColumn(name = "ID_NIVEL_ESCOLARIDADE", nullable = false)
	private NivelEscolaridade nivelEscolaridade;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_ENSINO_MEDIO", nullable = false)
	private TipoEnsinoMedio tipoEnsinoMedio;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private Set<ProfessorDeficiencia> deficiencia = new HashSet<>();
	
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private Set<TurmaProfessor> turmaProfessor = new HashSet<>();

}
