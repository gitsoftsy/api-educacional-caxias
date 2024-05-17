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
@Table(name = "TBL_ESCOLA_ENERGIA_ELETRICA")
@Data
public class EscolaEnergiaEletrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESCOLA_ENERGIA_ELETRICA")
    private Long idEscolaEnergiaEletrica;

    @ManyToOne
    @JoinColumn(name = "ID_ESCOLA", nullable = false)
    private Escola escola;

    @Column(name = "REDE_PUBLICA", nullable = false, length = 1)
    private Character redePublica;

    @Column(name = "GERADOR", nullable = false, length = 1)
    private Character gerador;

    @Column(name = "OUTROS", nullable = false, length = 1)
    private Character outros;

    @Column(name = "DESCRICAO_OUTROS", length = 255)
    private String descricaoOutros;

    @Column(name = "SEM_ENERGIA_ELETRICA", nullable = false, length = 1)
    private Character semEnergiaEletrica;
}