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
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_CANDIDATO_RELACIONAMENTO", 
    uniqueConstraints = { 
        @UniqueConstraint(name = "UQ_CANDIDATO_RELACIONAMNETO", columnNames = { "CANDIDATO_RELACIONAMENTO", "ID_CANDIDATO_RELACIONAMENTO"}),
        })
@Data
public class CandidatoRelacionamento {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    @JoinColumn(name = "ID_CANDIDATO_RELACIONAMENTO", nullable = false)
    private Long  candidatoRelacionamento;
    
    @Column(name = "ID_CANDIDATO",  nullable = false)
    private Candidato candidato;
   
    @ManyToOne
    @JoinColumn(name = "ID_PESSOA", nullable = false)
    private Pessoa pessoa;
    
    @ManyToOne
    @JoinColumn(name = "ID_PAPEL_PESSOAL", nullable = false)
    private PapelPessoa papaelPessoa;
    
    @Column(name = "ATIVO", nullable = false)
    private Character ativo;

    @Column(name = "DT_CADASTRO")
    private LocalDate dataCadastro;
}
