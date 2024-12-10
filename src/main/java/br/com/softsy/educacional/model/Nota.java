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

import lombok.Data;

@Entity
@Table(name = "TBL_NOTA")
@Data
public class Nota {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTA")
    private Long idNota;
	
    @ManyToOne
    @JoinColumn(name = "ID_ALUNO")
    private Aluno aluno;
    
    @ManyToOne
    @JoinColumn(name = "ID_PROVA")
    private Prova prova;
    
    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;
    
    @Column(name = "NOTA", length = 10, nullable = false)
    private String nota;
    
    @Column(name = "COMPARECEU", nullable = false)
    private Character compareceu;

    @Column(name = "ORDEM", length = 11, nullable = false)
    private Integer ordem;

}
