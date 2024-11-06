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
@Table(name = "TBL_OFERTA_CONCURSO")
@Data
public class OfertaConcurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_OFERTA_CONCURSO")
    private Long idOfertaConcurso;

    @ManyToOne
    @JoinColumn(name = "ID_CONCURSO", nullable = false)
    private Concurso concurso;

    @ManyToOne
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "ID_ESCOLA", nullable = false)
    private Escola escola;

    @ManyToOne
    @JoinColumn(name = "ID_TURNO", nullable = false)
    private Turno turno;
    
    @ManyToOne
    @JoinColumn(name = "ID_SERIE", nullable = false)
    private Serie serie;
    
    @Column(name = "DESCRICAO_OFERTA", length = 555, nullable = false)
    private String descricaoOferta;

    @Column(name = "VAGAS", nullable = false)
    private Integer vagas;

    @Column(name = "MIN_VAGAS_ABERT_TURMA")
    private Integer minVagasAbertTurma;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "ATIVO", nullable = false)
    private Character ativo;
}