package br.com.softsy.educacional.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_CONCURSO", 
    uniqueConstraints = { 
        @UniqueConstraint(name = "UQ_CONCURSO_CONCURSO", columnNames = { "CONCURSO", "ID_CONTA" })
    })
@Data
public class Concurso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONCURSO")
    private Long idConcurso;
    
    @ManyToOne
    @JoinColumn(name = "ID_CONTA", nullable = false)
    private Conta conta;
    
    @Column(name = "CONCURSO", nullable = false, length = 25)
    private String concurso;
    
    @ManyToOne
    @JoinColumn(name = "ID_PERIODO_LETIVO", nullable = false)
    private PeriodoLetivo periodoLetivo;
    
    @Column(name = "DT_ABERTURA")
    private LocalDate dataAbertura;
   
    @Column(name = "DT_FECHAMENTO")
    private LocalDate dataFechamento;
    
    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;
    
    @Column(name = "ATIVO", nullable = false)
    private Character ativo;
    
	@OneToMany(mappedBy = "concurso", cascade = CascadeType.ALL)
	private Set<OfertaConcurso> ofertaConcurso = new HashSet<>();

}