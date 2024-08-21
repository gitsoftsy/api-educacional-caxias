package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_ALUNO")
@Data
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUNO")
    private Long idAluno;

    @ManyToOne
    @JoinColumn(name = "ID_CONTA")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "ID_CURSO")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "ID_ESCOLA")
    private Escola escola;

    @ManyToOne
    @JoinColumn(name = "ID_SERIE")
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "ID_TURNO")
    private Turno turno;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOA")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "ID_CANDIDATO")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "ID_SITUACAO_ALUNO")
    private SituacaoAluno situacaoAluno;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "ALUNO", length = 40)
    private String aluno;

    @Column(name = "EMAIL_INTERNO", length = 500)
    private String emailInterno;

    @Column(name = "SENHA", length = 2500)
    private String senha;
}