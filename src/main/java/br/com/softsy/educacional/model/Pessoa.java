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
@Table(name = "TBL_PESSOA", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_PESSOA_CPF", columnNames = { "CPF", "ID_CONTA" }),
		@UniqueConstraint(name = "UQ_PESSOA_USUARIO", columnNames = { "USUARIO", "ID_CONTA" })
		})
@Data
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PESSOA")
	private Long idPessoa;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTA")
	private Conta conta;

	@Column(name = "NOME_COMPLETO", length = 255, nullable = false)
	private String nomeCompleto;

	@Column(name = "NOME_SOCIAL", length = 255)
	private String nomeSocial;

	@Column(name = "CPF", nullable = false, unique = true, length = 11)
	private String cpf;

	@Column(name = "RG_NUM", length = 255)
	private String rgNumero;

	@Column(name = "RG_ORG_EXPEDIDOR", length = 255)
	private String rgOrgaoExpedidor;

	@ManyToOne
	@JoinColumn(name = "ID_RG_UF_EMISSOR")
	private Uf rgUfEmissor;

	@Column(name = "RG_DATA_EXP")
	private LocalDate rgDataExpedicao;

	@Column(name = "RNE_NUM", length = 255)
	private String rneNumero;

	@Column(name = "RNE_ORG_EXPEDIDOR", length = 255)
	private String rneOrgaoExpedidor;

	@ManyToOne
	@JoinColumn(name = "ID_RNE_UF_EMISSOR")
	private Uf rneUfEmissor;

	@Column(name = "RNE_DATA_EXP")
	private LocalDate rneDataExpedicao;

	@Column(name = "CERT_NASC_NUM", length = 255)
	private String certidaoNascimentoNumero;

	@Column(name = "CERT_NASC_CARTORIO", length = 255)
	private String certidaoNascimentoCartorio;

	@ManyToOne
	@JoinColumn(name = "ID_CERT_NASC_UF_CARTORIO")
	private Uf certidaoNascimentoUfCartorio;

	@Column(name = "CERT_NASC_DATA_EMISSAO")
	private LocalDate certidaoNascimentoDataEmissao;

	@Column(name = "CERT_NASC_FOLHA", length = 255)
	private String certidaoNascimentoFolha;

	@Column(name = "CERT_NASC_LIVRO", length = 255)
	private String certidaoNascimentoLivro;

	@Column(name = "CERT_NASC_ORDEM", length = 255)
	private String certidaoNascimentoOrdem;

	@Column(name = "CERT_CASAMENTO_NUM", length = 255)
	private String certidaoCasamentoNumero;

	@Column(name = "CERT_CASAMENTO_CARTORIO", length = 255)
	private String certidaoCasamentoCartorio;

	@ManyToOne
	@JoinColumn(name = "ID_CERT_CASAMENTO_UF_CARTORIO")
	private Uf certidaoCasamentoUfCartorio;

	@Column(name = "CERT_CASAMENTO_DATA_EMISSAO")
	private LocalDate certidaoCasamentoDataEmissao;

	@Column(name = "CERT_CASAMENTO_FOLHA", length = 255)
	private String certidaoCasamentoFolha;

	@Column(name = "CERT_CASAMENTO_LIVRO", length = 255)
	private String certidaoCasamentoLivro;

	@Column(name = "CERT_CASAMENTO_ORDEM", length = 255)
	private String certidaoCasamentoOrdem;

	@Column(name = "DT_NASCIMENTO")
	private LocalDate dtNascimento;

	@Column(name = "SEXO", length = 1, nullable = false)
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
	@JoinColumn(name = "ID_NACIONALIDADE")
	private Nacionalidade nacionalidadeId;

	@ManyToOne
	@JoinColumn(name = "ID_MUNICIPIO_NASCIMENTO")
	private Municipio municipioNascimento;

	@ManyToOne
	@JoinColumn(name = "ID_PAIS_RESIDENCIA")
	private Pais paisResidencia;

	@Column(name = "NACIONALIDADE", length = 2)
	private String nacionalidade;

	@Column(name = "NOME_PAI", length = 255)
	private String nomePai;

	@Column(name = "NOME_MAE", length = 255)
	private String nomeMae;

	@Column(name = "CEP", length = 8)
	private String cep;

	@Column(name = "ENDERECO", length = 555)
	private String endereco;

	@Column(name = "NUMERO", length = 20)
	private String numero;

	@Column(name = "COMPLEMENTO", length = 200)
	private String complemento;

	@Column(name = "BAIRRO", length = 200)
	private String bairro;

	@Column(name = "MUNICIPIO", length = 555)
	private String municipio;

	@Column(name = "DISTRITO", length = 555)
	private String distrito;

	@Column(name = "UF", length = 2)
	private String uf;

	@Column(name = "DT_CADASTRO")
	private LocalDateTime dataCadastro;

	@Column(name = "TELEFONE", length = 15)
	private String telefone;

	@Column(name = "CELULAR", length = 15)
	private String celular;

	@Column(name = "EMAIL", length = 555)
	private String email;

	@Column(name = "EMPRESA", length = 555)
	private String empresa;

	@Column(name = "OCUPACAO", length = 255)
	private String ocupacao;

	@Column(name = "TELEFONE_COMERCIAL", length = 15)
	private String telefoneComercial;

	@Column(name = "USUARIO", length = 50)
	private String usuario;

	@Column(name = "SENHA", length = 500)
	private String senha;

	@Column(name = "ATIVO", length = 1, nullable = false)
	private Character ativo;

	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private Set<PessoaNacionalidade> nacionalidades = new HashSet<>();

}
