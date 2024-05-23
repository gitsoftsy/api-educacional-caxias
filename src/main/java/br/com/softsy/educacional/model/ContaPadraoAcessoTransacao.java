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
@Table(name = "TBL_CONTA_PADRAO_ACESSO_TRANSACAO")
@Data
public class ContaPadraoAcessoTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTA_PADRAO_ACESSO_TRANSACAO")
    private Long idContaPadraoAcessoTransacao;
    
    @ManyToOne
    @JoinColumn(name = "ID_CONTA_PADRAO_ACESSO", nullable = false)
    private ContaPadraoAcesso contaPadraoAcesso;

    @ManyToOne
    @JoinColumn(name = "ID_TRANSACAO", nullable = false)
    private Transacao transacao;

    @Column(name = "ACESSA", nullable = false)
    private Character acessa;

    @Column(name = "ALTERA", nullable = false)
    private Character altera;
}