package br.com.softsy.educacional.model;

import java.time.LocalDate;
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
@Table(name = "TBL_CANDIDATO_RELACIONAMENTO") 
@Data
public class CandidatoRelacionamento {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CANDIDATO_RELACIONAMENTO", nullable = false)
    private Long  idCandidatoRelacionamento;
    
    @ManyToOne
    @JoinColumn(name = "ID_CANDIDATO",  nullable = false)
    private Candidato candidato;
   
    @ManyToOne
    @JoinColumn(name = "ID_PESSOA", nullable = false)
    private Pessoa pessoa;
    
    @ManyToOne
    @JoinColumn(name = "ID_PAPEL_PESSOA", nullable = false)
    private PapelPessoa papelPessoa;
    
    @Column(name = "ATIVO", nullable = false)
    private Character ativo;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;
}
