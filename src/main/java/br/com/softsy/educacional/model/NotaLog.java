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
@Table(name = "TBL_NOTA_LOG")
@Data
public class NotaLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTA_LOG")
    private Long idNotaLog;

    @ManyToOne
    @JoinColumn(name = "ID_ALUNO", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "ID_PROVA", nullable = false)
    private Prova prova;

    @ManyToOne
    @JoinColumn(name = "ID_NOTA", nullable = false)
    private Nota nota;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "OPERACAO", length = 1)
    private Character operacao;

    @Column(name = "NOTA_ANTERIOR", length = 10)
    private String notaAnterior;

    @Column(name = "NOTA_ATUAL", length = 10)
    private String notaAtual;

    @ManyToOne
    @JoinColumn(name = "ID_PROFESSOR", nullable = true)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;

}
