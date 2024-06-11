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
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_TIPO_INGRESSO", 
    uniqueConstraints = { 
        @UniqueConstraint(name = "UQ_TIPO_INGRESSO", columnNames = { "TIPO_INGRESSO", "ID_CONTA" })
        })
@Data
public class TipoIngresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_INGRESSO")
    private Long idTipoIngresso;
    
    @ManyToOne
    @JoinColumn(name = "ID_CONTA", nullable = false)
    private Conta conta;
    
    @Column(name = "TIPO_INGRESSO", nullable = false)
    private String tipoIngresso;
    
    @Column(name = "ATIVO", nullable = false)
    private Character ativo;
    
    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;
}