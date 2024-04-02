package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;
import java.util.Date;

import br.com.softsy.educacional.model.Pessoa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaDTO {

    private Long idPessoa;
    private String nome;
    private String cpf;
    private Date dtNascimento;
    private Character sexo;
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
    private Character ativo;

    private RacaDTO raca;
    private PaisDTO paisNascimento;
    private UfDTO ufNascimento;
    private MunicipioDTO municipioNascimento;
    private PaisDTO paisResidencia;

    public PessoaDTO(Pessoa pessoa) {
        this.idPessoa = pessoa.getIdPessoa();
        this.nome = pessoa.getNome();
        this.cpf = pessoa.getCpf();
        this.dtNascimento = pessoa.getDtNascimento();
        this.sexo = pessoa.getSexo();
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

        this.raca = new RacaDTO(pessoa.getRaca());
        this.paisNascimento = new PaisDTO(pessoa.getPaisNascimento());
        this.ufNascimento = new UfDTO(pessoa.getUfNascimento());
        this.municipioNascimento = new MunicipioDTO(pessoa.getMunicipioNascimento());
        this.paisResidencia = new PaisDTO(pessoa.getPaisResidencia());
    }
}
