package br.com.softsy.educacional.model;

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
@Table(name = "TBL_ESCOLA_LIXO")
@Data
public class EscolaLixo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESCOLA_LIXO")
    private Long idEscolaLixo;

    @ManyToOne
    @JoinColumn(name = "ID_ESCOLA", nullable = false)
    private Escola escola;

    @Column(name = "COLETA_PERIODICA", nullable = false, length = 1)
    private Character coletaPeriodica;

    @Column(name = "QUEIMA_LIXO", nullable = false, length = 1)
    private Character queimaLixo;

    @Column(name = "JOGA_OUTRA_AREA", nullable = false, length = 1)
    private Character jogaOutraArea;

    @Column(name = "RECICLAGEM", nullable = false, length = 1)
    private Character reciclagem;

    @Column(name = "ENTERRA", nullable = false, length = 1)
    private Character enterra;

    @Column(name = "OUTROS", nullable = false, length = 1)
    private Character outros;

    @Column(name = "DESCRICAO_OUTROS", length = 255)
    private String descricaoOutros;
}