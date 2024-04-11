package br.com.softsy.educacional.model;

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

import lombok.Data;

@Entity
@Table(name = "TBL_TURMA")
@Data
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TURMA")
	private Long idTurma;
	
	@ManyToOne
	@JoinColumn(name = "ID_ESCOLA", nullable = true)
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name = "ID_ANO_ESCOLAR", nullable = true)
	private AnoEscolar anoEscolar;
	
	@Column(name = "NUM_TURMA", nullable = false)
	private String numTurma;
	
	@Column(name = "COD_TURMA_INEP", nullable = false)
	private String codTurmaInep;
	
	@ManyToOne
	@JoinColumn(name = "ID_FORMA_ORGAN_ENSINO", nullable = true)
	private FormaOrganEnsino formaOrganEnsino;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_DE_MEDICAO", nullable = true)
	private TipoDeMedicao tipoDeMedicao;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURNO", nullable = true)
	private Turno turno;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_ATENDIMENTO", nullable = true)
	private TipoAtendimento tipoAtendimento;
	
	@ManyToOne
	@JoinColumn(name = "ID_MODALIDADE_ESCOLA", nullable = true)
	private ModalidadeEscola modalidadeEscola;
	
	@Column(name = "LIBRAS", nullable = false)
	private Character libras;
	
	
	@OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
	private Set<TurmaProfessor> turmaProfessor = new HashSet<>();
}
