package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import br.com.softsy.educacional.model.Pessoa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroPessoaDTO {

    private Long idPessoa;
    
    @NotNull
    private Long contaId;

    @NotNull
    private String nomeCompleto;

    private String nomeSocial;

    @CPF
    private String cpf;

    private String rgNumero;

    private String rgOrgaoExpedidor;

    private Long rgUfEmissorId;

    private LocalDate rgDataExpedicao;

    private String rneNumero;

    private String rneOrgaoExpedidor;

    private Long rneUfEmissorId;

    private LocalDate rneDataExpedicao;

    private String certidaoNascimentoNumero;

    private String certidaoNascimentoCartorio;

    private Long certidaoNascimentoMunicipioCartorioId;

    private LocalDate certidaoNascimentoDataEmissao;

    private String certidaoNascimentoFolha;

    private String certidaoNascimentoLivro;

    private String certidaoNascimentoOrdem;

    private String certidaoCasamentoNumero;

    private String certidaoCasamentoCartorio;

    private Long certidaoCasamentoMunicipioCartorioId;

    private LocalDate certidaoCasamentoDataEmissao;

    private String certidaoCasamentoFolha;

    private String certidaoCasamentoLivro;

    private String certidaoCasamentoOrdem;

    @NotNull
    private LocalDate dtNascimento;

    @NotNull
    private Character sexo;

    private Long racaId;

    private Long paisNascimentoId;

    private Long nacionalidadeId;

    private Long municipioNascimentoId;

    private Long paisResidenciaId;


    private String nomePai;

    private String nomeMae;

    
    private String cep;

    
    private String endereco;

    
    private String numero;

    private String complemento;

    private String bairro;

   
    private String municipio;

    private String distrito;

    
    private String uf;

    private LocalDateTime dataCadastro;

    private String telefone;

    private String celular;
    
	private String estadoCivil;

    private String email;

    private String empresa;

    private String ocupacao;

    private String telefoneComercial;

    private String usuario;

    private String senha;

    private Character ativo;

    public CadastroPessoaDTO(Pessoa pessoa) {
        this.idPessoa = pessoa.getIdPessoa();
        this.contaId = pessoa.getConta().getIdConta();
        this.nomeCompleto = pessoa.getNomeCompleto();
        this.nomeSocial = pessoa.getNomeSocial();
        this.cpf = pessoa.getCpf();
        this.estadoCivil = pessoa.getEstadoCivil();
        this.rgNumero = pessoa.getRgNumero();
        this.rgOrgaoExpedidor = pessoa.getRgOrgaoExpedidor();
        this.rgUfEmissorId = pessoa.getRgUfEmissor() != null ? pessoa.getRgUfEmissor().getIdUf() : null;
        this.rgDataExpedicao = pessoa.getRgDataExpedicao();
        this.rneNumero = pessoa.getRneNumero();
        this.rneOrgaoExpedidor = pessoa.getRneOrgaoExpedidor();
        this.rneUfEmissorId = pessoa.getRneUfEmissor() != null ? pessoa.getRneUfEmissor().getIdUf() : null;
        this.rneDataExpedicao = pessoa.getRneDataExpedicao();
        this.certidaoNascimentoNumero = pessoa.getCertidaoNascimentoNumero();
        this.certidaoNascimentoCartorio = pessoa.getCertidaoNascimentoCartorio();
        this.certidaoNascimentoMunicipioCartorioId = pessoa.getCertidaoNascimentoMunicipioCartorio() != null ? pessoa.getCertidaoNascimentoMunicipioCartorio().getIdMunicipio() : null;
        this.certidaoNascimentoDataEmissao = pessoa.getCertidaoNascimentoDataEmissao();
        this.certidaoNascimentoFolha = pessoa.getCertidaoNascimentoFolha();
        this.certidaoNascimentoLivro = pessoa.getCertidaoNascimentoLivro();
        this.certidaoNascimentoOrdem = pessoa.getCertidaoNascimentoOrdem();
        this.certidaoCasamentoNumero = pessoa.getCertidaoCasamentoNumero();
        this.certidaoCasamentoCartorio = pessoa.getCertidaoCasamentoCartorio();
        this.certidaoCasamentoMunicipioCartorioId = pessoa.getCertidaoCasamentoMunicipioCartorio() != null ? pessoa.getCertidaoCasamentoMunicipioCartorio().getIdMunicipio() : null;
        this.certidaoCasamentoDataEmissao = pessoa.getCertidaoCasamentoDataEmissao();
        this.certidaoCasamentoFolha = pessoa.getCertidaoCasamentoFolha();
        this.certidaoCasamentoLivro = pessoa.getCertidaoCasamentoLivro();
        this.certidaoCasamentoOrdem = pessoa.getCertidaoCasamentoOrdem();
        this.dtNascimento = pessoa.getDtNascimento();
        this.sexo = pessoa.getSexo();
        this.racaId = pessoa.getRaca().getIdRaca();
        this.paisNascimentoId = pessoa.getPaisNascimento().getIdPais();
        this.nacionalidadeId = pessoa.getNacionalidadeId().getIdNacionalidade();
        this.municipioNascimentoId = pessoa.getMunicipioNascimento().getIdMunicipio();
        this.paisResidenciaId = pessoa.getPaisResidencia().getIdPais();
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
        this.ativo = pessoa.getAtivo();    }
}