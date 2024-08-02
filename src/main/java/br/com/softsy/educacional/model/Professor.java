package br.com.softsy.educacional.model;
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
@Table(name = "TBL_PROFESSOR", 
    uniqueConstraints = { 
        @UniqueConstraint(name = "UQ_PROFESSOR", columnNames = { "USUARIO", "ID_CONTA" })
        })
@Data
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROFESSOR")
    private Long idProfessor;
    
    @ManyToOne
    @JoinColumn(name = "ID_CONTA", nullable = false)
    private Conta conta;
    
    @ManyToOne
    @JoinColumn(name = "ID_PESSOA", nullable = false)
    private Pessoa pessoa;
    
    @Column(name = "CODIGO_INEP")
    private String codigoInep;
    
    @Column(name = "MATRICULA")
    private String matricula;
    
    @Column(name = "USUARIO", nullable = false)
    private String usuario;
    
    @Column(name = "SENHA", nullable = false)
    private String senha;
    
    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "ATIVO", nullable = false)
    private Character ativo;
    
    @Column(name = "EMAIL_INSTITUCIONAL")
    private String emailInstitucional;
    
    @Column(name = "DT_CONTRATACAO")
    private LocalDate dataContratacao;
    
    @Column(name = "DT_DEMISSAO")
    private LocalDate dataDemissao;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private Set<ProfessorDeficiencia> deficiencia = new HashSet<>();
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private Set<TurmaProfessor> turmaProfessor = new HashSet<>();

}