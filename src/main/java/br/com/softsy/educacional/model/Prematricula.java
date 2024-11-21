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
@Table(name = "TBL_PREMATRICULA")
@Data
public class Prematricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PREMATRICULA")
    private Long idPrematricula;

    @ManyToOne
    @JoinColumn(name = "ID_CONTA")
    private Conta conta;

    @Column(name = "ATIVO", nullable = false)
    private Character ativo;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_MATRICULA")
    private TipoMatricula tipoMatricula;

    @ManyToOne
    @JoinColumn(name = "ID_ALUNO", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "ID_PERIODO_LETIVO")
    private PeriodoLetivo periodoLetivo;

    @ManyToOne
    @JoinColumn(name = "ID_DISCIPLINA")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "ID_TURMA")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "ID_SERIE")
    private Serie serie;

    @Column(name = "MSG_ERRO", length = 555)
    private String mensagemErro;

    @Column(name = "MANUAL", nullable = false)
    private Character manual;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "DT_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;

    @Column(name = "DT_CONFIRMACAO")
    private LocalDateTime dataConfirmacao;

    @Column(name = "OBS", length = 555)
    private String observacao;
}
