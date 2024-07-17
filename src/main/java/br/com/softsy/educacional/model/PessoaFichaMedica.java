package br.com.softsy.educacional.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "TBL_PESSOA_FICHA_MEDICA")
@Data
public class PessoaFichaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOA_FICHA_MEDICA")
    private Long idPessoaFichaMedica;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOA")
    private Pessoa pessoa;

    @Column(name = "DT_CADASTRO")
    private LocalDateTime dtCadastro;

    @ManyToOne
    @JoinColumn(name = "ID_RESPONSAVEL_PESSOA")
    private Pessoa responsavelPessoa;

    @Column(name = "PESO", precision = 10, scale = 2)
    private BigDecimal peso;

    @Column(name = "ALTURA", precision = 10, scale = 2)
    private BigDecimal altura;

    @Column(name = "TIPO_SANGUINEO", length = 30)
    private String tipoSanguineo;

    @Column(name = "ACEITA_TRANSFUSAO", length = 1)
    private Character aceitaTransfusao;

    @Column(name = "NUMERO_CART_SUS", length = 20)
    private String numeroCartSus;

    @Column(name = "PLANO_SAUDE", length = 50)
    private String planoSaude;

    @Column(name = "NUMERO_CARTEIRINHA", length = 30)
    private String numeroCarteirinha;

    @Column(name = "PS_EMERGENCIA_CEP", length = 8)
    private String psEmergenciaCep;

    @Column(name = "PS_EMERGENCIA_ENDERECO", length = 555)
    private String psEmergenciaEndereco;

    @Column(name = "PS_EMERGENCIA_NUMERO", length = 20)
    private String psEmergenciaNumero;

    @Column(name = "PS_EMERGENCIA_COMPLEMENTO", length = 200)
    private String psEmergenciaComplemento;

    @Column(name = "PS_EMERGENCIA_BAIRRO", length = 200)
    private String psEmergenciaBairro;

    @Column(name = "PS_EMERGENCIA_MUNICIPIO", length = 555)
    private String psEmergenciaMunicipio;

    @Column(name = "PS_EMERGENCIA_DISTRITO", length = 555)
    private String psEmergenciaDistrito;

    @Column(name = "PS_EMERGENCIA_UF", length = 2)
    private String psEmergenciaUf;

    @Column(name = "PS_EMERGENCIA_TELEFONE", length = 11)
    private String psEmergenciaTelefone;

    @Column(name = "ALERGIA", length = 1)
    private Character alergia;

    @Column(name = "DESCRICAO_ALERGIA", length = 2550)
    private String descricaoAlergia;

    @Column(name = "TRATAMENTO_MEDICO", length = 1)
    private Character tratamentoMedico;

    @Column(name = "DESCRICAO_TRATAMENTO_MEDICO", length = 2550)
    private String descricaoTratamentoMedico;

    @Column(name = "COMORBIDADES", length = 1)
    private Character comorbidades;

    @Column(name = "DESCRICAO_COMORBIDADES", length = 2550)
    private String descricaoComorbidades;

    @Column(name = "OUTRAS_DOENCAS", length = 2550)
    private String outrasDoencas;

}