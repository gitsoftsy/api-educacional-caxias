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
@Table(name = "TBL_CONTA_PADRAO_ACESSO")
@Data
public class ContaPadraoAcesso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTA_PADRAO_ACESSO")
    private Long idContaPadraoAcesso;
    
    @Column(name = "DT_CADASTRO")
    private LocalDateTime dtCadastro;
    
    @Column(name = "ATIVO", nullable = false)
    private Character ativo;
    
    @Column(name = "PADRAO_ACESSO", length = 150, nullable = false, unique = true)
    private String padraoAcesso;
    
    @ManyToOne
    @JoinColumn(name = "ID_CONTA", nullable = false)
    private Conta conta;
}