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
@Table(name = "TBL_UTM")
@Data
public class Utm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UTM")
    private Long idUtm;

    @ManyToOne
    @JoinColumn(name = "ID_CONTA", nullable = false)
    private Conta conta;

    @Column(name = "UTM_NOME", length = 25, nullable = false)
    private String utmNome;

    @Column(name = "DESCRICAO", length = 255)
    private String descricao;

    @Column(name = "DT_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "ATIVO", nullable = false)
    private Character ativo;
    
}