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
@Table(name = "TBL_SITUACAO_ALUNO")
@Data
public class SituacaoAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SITUACAO_ALUNO")
    private Long idSituacaoAluno;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "SITUACAO_ALUNO", length = 50)
    private String situacaoAluno;

    @ManyToOne
    @JoinColumn(name = "ID_CONTA")
    private Conta conta;
}