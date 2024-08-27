package br.com.softsy.educacional.model;

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
@Table(name = "TBL_CRITERIO_AVALIACAO")
@Data
public class CriterioAvaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CRITERIO_AVALIACAO")
    private Long idCriterioAvaliacao;

    @ManyToOne
    @JoinColumn(name = "ID_TURMA", nullable = false)
    private Turma turma;

    @Column(name = "COD_CRITERIO_AVALICAO", nullable = false)
    private String codCriterioAvaliacao;
    
    @Column(name = "CRITERIO_AVALIACAO", nullable = false)
    private String criterioAvaliacao;

    @Column(name = "ORDEM", nullable = false)
    private Integer ordem;

    @Column(name = "DT_PROVA", nullable = false)
    private LocalDate dataProva;

    @Column(name = "ATIVO", nullable = false)
    private Character ativo;
}