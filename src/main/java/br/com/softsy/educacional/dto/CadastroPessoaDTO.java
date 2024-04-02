package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Pessoa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroPessoaDTO {

    private Long idPessoa;

    @NotNull
    private String nome;

    @NotNull
    private String cpf;

    @NotNull
    private Date dtNascimento;

    @NotNull
    private Character sexo;

    private Long racaId;

    private Long paisNascimentoId;

    private Long ufNascimentoId;

    private Long municipioNascimentoId;

    private Long paisResidenciaId;

    private String nomePai;

    private String nomeMae;

    @NotNull
    private String cep;

    @NotNull
    private String endereco;

    @NotNull
    private String numero;

    private String complemento;

    private String bairro;

    @NotNull
    private String municipio;

    private String distrito;

    @NotNull
    private String uf;

    private LocalDateTime dataCadastro;

    private Character ativo;

    public CadastroPessoaDTO(Pessoa pessoa) {
        this.idPessoa = pessoa.getIdPessoa();
        this.nome = pessoa.getNome();
        this.cpf = pessoa.getCpf();
        this.dtNascimento = pessoa.getDtNascimento();
        this.sexo = pessoa.getSexo();
        this.racaId = pessoa.getRaca().getIdRaca();
        this.paisNascimentoId = pessoa.getPaisNascimento().getIdPais();
        this.ufNascimentoId = pessoa.getUfNascimento().getIdUf();
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
        this.ativo = pessoa.getAtivo();
    }
}