package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Pessoa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaDTO {

    private Long idPessoa;
    private ContaDTO conta;
    private String nomeCompleto;
    private String nomeSocial;
    private String cpf;
    private String rgNumero;
    private String rgOrgaoExpedidor;
    private UfDTO rgUfEmissor;
    private LocalDate rgDataExpedicao;
    private String rneNumero;
    private String rneOrgaoExpedidor;
    private UfDTO rneUfEmissor;
    private LocalDate rneDataExpedicao;
    private String certidaoNascimentoNumero;
    private String certidaoNascimentoCartorio;
    private MunicipioDTO certidaoNascimentoMunicipioCartorio;
    private LocalDate certidaoNascimentoDataEmissao;
    private String certidaoNascimentoFolha;
    private String certidaoNascimentoLivro;
    private String certidaoNascimentoOrdem;
    private String certidaoCasamentoNumero;
    private String certidaoCasamentoCartorio;
    private MunicipioDTO certidaoCasamentoMunicipioCartorio;
    private LocalDate certidaoCasamentoDataEmissao;
    private String certidaoCasamentoFolha;
    private String certidaoCasamentoLivro;
    private String certidaoCasamentoOrdem;
    private LocalDate dtNascimento;
    private Character sexo;
    private RacaDTO raca;
    private PaisDTO paisNascimento;
    private NacionalidadeDTO nacionalidadeId;
    private MunicipioDTO municipioNascimento;
    private PaisDTO paisResidencia;
    private String nomePai;
    private String nomeMae;
    private String cep;
    private String endereco;
	private String estadoCivil;
    private String numero;
    private String complemento;
    private String bairro;
    private String municipio;
    private String distrito;
    private String uf;
    private LocalDateTime dataCadastro;
    private String telefone;
    private String celular;
    private String email;
    private String empresa;
    private String ocupacao;
    private String telefoneComercial;
    private String usuario;
    private String senha;
    private Character ativo;

    public PessoaDTO(Pessoa pessoa) {
        this.idPessoa = pessoa.getIdPessoa();
        this.nomeCompleto = pessoa.getNomeCompleto();
        this.nomeSocial = pessoa.getNomeSocial();
        this.cpf = pessoa.getCpf();
        this.conta = new ContaDTO(pessoa.getConta());
        this.rgNumero = pessoa.getRgNumero();
        this.estadoCivil = pessoa.getEstadoCivil();
        this.rgOrgaoExpedidor = pessoa.getRgOrgaoExpedidor();
        
		if (pessoa.getRgUfEmissor() != null) {
			this.rgUfEmissor = new UfDTO(pessoa.getRgUfEmissor());
		} else {
			this.rgUfEmissor = null;
		}
        
        this.rgDataExpedicao = pessoa.getRgDataExpedicao();
        this.rneNumero = pessoa.getRneNumero();
        this.rneOrgaoExpedidor = pessoa.getRneOrgaoExpedidor();
        
		if (pessoa.getRneUfEmissor() != null) {
			this.rneUfEmissor = new UfDTO(pessoa.getRneUfEmissor());
		} else {
			this.rneUfEmissor = null;
		}
		
        this.rneDataExpedicao = pessoa.getRneDataExpedicao();
        this.certidaoNascimentoNumero = pessoa.getCertidaoNascimentoNumero();
        this.certidaoNascimentoCartorio = pessoa.getCertidaoNascimentoCartorio();
        this.certidaoNascimentoMunicipioCartorio = new MunicipioDTO(pessoa.getCertidaoNascimentoMunicipioCartorio());
        this.certidaoNascimentoDataEmissao = pessoa.getCertidaoNascimentoDataEmissao();
        this.certidaoNascimentoFolha = pessoa.getCertidaoNascimentoFolha();
        this.certidaoNascimentoLivro = pessoa.getCertidaoNascimentoLivro();
        this.certidaoNascimentoOrdem = pessoa.getCertidaoNascimentoOrdem();
        this.certidaoCasamentoNumero = pessoa.getCertidaoCasamentoNumero();
        this.certidaoCasamentoCartorio = pessoa.getCertidaoCasamentoCartorio();
        this.certidaoCasamentoMunicipioCartorio = new MunicipioDTO(pessoa.getCertidaoCasamentoMunicipioCartorio());
        this.certidaoCasamentoDataEmissao = pessoa.getCertidaoCasamentoDataEmissao();
        this.certidaoCasamentoFolha = pessoa.getCertidaoCasamentoFolha();
        this.certidaoCasamentoLivro = pessoa.getCertidaoCasamentoLivro();
        this.certidaoCasamentoOrdem = pessoa.getCertidaoCasamentoOrdem();
        this.dtNascimento = pessoa.getDtNascimento();
        this.sexo = pessoa.getSexo();
        this.raca = new RacaDTO(pessoa.getRaca());
        this.paisNascimento = new PaisDTO(pessoa.getPaisNascimento());
        this.nacionalidadeId = new NacionalidadeDTO(pessoa.getNacionalidadeId());
        
		if (pessoa.getMunicipioNascimento() != null) {
			this.municipioNascimento = new MunicipioDTO(pessoa.getMunicipioNascimento());
		} else {
			this.municipioNascimento = null;
		}
        
        this.paisResidencia = new PaisDTO(pessoa.getPaisResidencia());
        this.nomePai = pessoa.getNomePai();
        this.nomeMae = pessoa.getNomeMae();
        this.cep = pessoa.getCep();
        this.endereco = pessoa.getEndereco();
        this.numero = pessoa.getNumero();
        this.complemento = pessoa.getComplemento();
        this.bairro = pessoa.getBairro();
        this.municipio = pessoa.getMunicipio();
        this.distrito = pessoa.getDistrito();
        this.uf = pessoa.getUf();
        this.dataCadastro = pessoa.getDataCadastro();
        this.telefone = pessoa.getTelefone();
        this.celular = pessoa.getCelular();
        this.email = pessoa.getEmail();
        this.empresa = pessoa.getEmpresa();
        this.ocupacao = pessoa.getOcupacao();
        this.telefoneComercial = pessoa.getTelefoneComercial();
        this.usuario = pessoa.getUsuario();
        this.senha = pessoa.getSenha();
        this.ativo = pessoa.getAtivo();
    }
}
