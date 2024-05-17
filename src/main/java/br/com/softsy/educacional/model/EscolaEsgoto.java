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
@Table(name = "TBL_ESCOLA_ESGOTO")
@Data
public class EscolaEsgoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESCOLA_ESGOTO")
    private Long idEscolaEsgoto;

    @ManyToOne
    @JoinColumn(name = "ID_ESCOLA", nullable = false)
    private Escola escola;

    @Column(name = "REDE_PUBLICA", nullable = false, length = 1)
    private Character redePublica;

    @Column(name = "FOSSA", nullable = false, length = 1)
    private Character fossa;

    @Column(name = "INEXISTENTE", nullable = false, length = 1)
    private Character inexistente;
}