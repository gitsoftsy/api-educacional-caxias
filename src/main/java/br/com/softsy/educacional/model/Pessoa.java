package br.com.softsy.educacional.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
@Table(name = "TBL_PESSOA", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_PESSOA", columnNames = { "CPF", "ID_DEPENDENCIA_ADMINISTRATIVA" })
		})
@Data
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PESSOA")
	private Long idPessoa;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "CPF", nullable = false, unique = true, length = 11)
	private String cpf;
	
	@Column(name = "DT_NASCIMENTO", nullable = false)
	private LocalDate dtNascimento;
	
	@Column(name = "SEXO", nullable = false)
	private Character sexo;
	
	@ManyToOne
	@JoinColumn(name = "ID_RACA")
	private Raca raca;
	
	@ManyToOne
	@JoinColumn(name = "ID_PAIS_NASCIMENTO")
	private Pais paisNascimento;
	
	@ManyToOne
	@JoinColumn(name = "ID_UF_NASCIMENTO")
	private Uf ufNascimento;
	
	@ManyToOne
	@JoinColumn(name = "ID_MUNICIPIO_NASCIMENTO")
	private Municipio municipioNascimento;
	
	@ManyToOne
	@JoinColumn(name = "ID_PAIS_RESIDENCIA")
	private Pais paisResidencia;
	
	@Column(name = "NOME_PAI")
	private String nomePai;
	
	@Column(name = "NOME_MAE")
	private String nomeMae;
	
	@Column(name = "CEP", nullable = false, length = 8)
	private String cep;
	
	@Column(name = "ENDERECO", nullable = false)
	private String endereco;
	
	@Column(name = "NUMERO", nullable = false)
	private String numero;
	
	@Column(name = "COMPLEMENTO")
	private String complemento;
	
	@Column(name = "BAIRRO")
	private String bairro;
	
	@Column(name = "MUNICIPIO", nullable = false)
	private String municipio;
	
	@Column(name = "DISTRITO")
	private String distrito;
	
	@Column(name = "UF", nullable = false, length = 2)
	private String uf;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private Set<PessoaNacionalidade> nacionalidade = new HashSet<>();

}
