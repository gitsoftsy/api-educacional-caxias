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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "TBL_ESCOLA")
@Data
public class Escola {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESCOLA")
	private Long idEscola;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
	
	@Column(name = "NOME_ESCOLA", nullable = false)
	private String nomeEscola;
	
	@Column(name = "LOGO_ESCOLA", nullable = false)
	private String logoEscola;
	
	@Column(name = "TIPO_ESCOLA", nullable = false)
	private Character tipoEscola;
	
	@Column(name = "CNPJ", nullable = false)
	private String cnpj;
	
	@Column(name = "CODIGO_INEP")
	private String codigoInep;
	
	@Column(name = "CEP", nullable =  false, length = 8)
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
	
	@Column(name = "UF", nullable = false)
	private String uf;
	
	@Column(name = "NUM_CME")
	private String numCME;
	
	@Column(name = "NUM_PARECER_CME")
	private String numParecerCME;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "EDUCACAO_INDIGENA", nullable = false)
	private Character educacaoIndigena;
	
	@Column(name = "EXAME_SELECAO", nullable = false)
	private Character exameSelecao;
	
	@Column(name = "COMPARTILHA_ESPACO", nullable = false)
	private Character compartilhaEspaco;
	
	@Column(name = "USA_ESPACO_ENTORNO_ESCOLAR", nullable = false)
	private Character usaEspacoEntornoEscolar;
	
	@Column(name = "PPP_ATUALIZADO_12_MESES", nullable = false)
	private Character pppAtualizado12Meses;
	
	@Column(name = "ACESSIVEL", nullable = false)
	private Character acessivel;
	
	@ManyToOne
	@JoinColumn(name = "ID_LOCALIZACAO", nullable = true)
	private Localizacao localizacao;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPENDENCIA_ADMINISTRATIVA", nullable = false)
	private DependenciaAdministrativa dependenciaAdm;
	
	@ManyToOne
	@JoinColumn(name = "ID_SITUACAO_FUNCIONAMENTO", nullable = false)
	private SituacaoFuncionamento situacaoFuncionamento;
	
	@ManyToOne
	@JoinColumn(name = "ID_FORMA_OCUPACAO_PREDIO", nullable = false)
	private FormaOcupacaoPredio formaOcupacaoPredio;
	
	@OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
	private Set<EscolaTelefone> telefones = new HashSet<>();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
