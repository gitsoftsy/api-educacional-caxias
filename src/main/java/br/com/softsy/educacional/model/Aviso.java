package br.com.softsy.educacional.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_AVISO")
@Data
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AVISO")
    private Long idAviso;
    
	@ManyToOne
	@JoinColumn(name = "ID_CONTA")
	private Conta conta;
	
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_AVISO", nullable = false)
    private TipoAviso tipoAviso;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

    @Column(name = "DTINI")
    private Date dataInicio;

    @Column(name = "DTFIM")
    private Date dataFim;

    @Column(name = "TITULO", length = 255, nullable = false)
    private String titulo;

    @Column(name = "MENSAGEM")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_PROFESSOR", nullable = true)
    private Professor professor;

    @Column(name = "PATH_ANEXO", length = 255)
    private String pathAnexo;

    @Column(name = "PERMITE_RESPOSTA", length = 1)
    private Character permiteResposta;
    
    @ManyToMany
    @JoinTable(
        name = "TBL_AVISO_DESTINATARIO",
        joinColumns = @JoinColumn(name = "ID_AVISO"),
        inverseJoinColumns = @JoinColumn(name = "ID_ALUNO")
    )
    private List<Aluno> destinatarios;

	
	
}
