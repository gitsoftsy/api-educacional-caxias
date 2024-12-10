package br.com.softsy.educacional.model;

import java.time.LocalDateTime;
import java.time.LocalDate;

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
@Table(name = "TBL_PROVA")
@Data
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVA")
    private Long idProva;

    @ManyToOne
    @JoinColumn(name = "ID_TURMA")
    private Turma turma;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "NOME_ABREVIADO", length = 5, nullable = false)
    private String nomeAbreviado;

    @Column(name = "DESCRICAO", length = 55, nullable = false)
    private String descricao;

    @Column(name = "DT_DIVULGACAO")
    private LocalDate dataDivulgacao;

    @Column(name = "DT_AGENDA_PROVA")
    private LocalDateTime dataAgendaProva;

    @Column(name = "ATIVO", length = 1, nullable = false)
    private Character ativo;

    @Column(name = "ORDEM")
    private Integer ordem;

    @Column(name = "TIPO_CONCEITO", length = 1, nullable = false)
    private Character tipoConceito;

    @Column(name = "CONCEITO_MAX", length = 10)
    private String conceitoMax;

    @Column(name = "DT_LIMITE_REVISAO")
    private LocalDate dataLimiteRevisao;

    @Column(name = "EH_SIMULADO", length = 1, nullable = false)
    private Character ehSimulado;

    @Column(name = "FORMULA", length = 255)
    private String formula;
}
