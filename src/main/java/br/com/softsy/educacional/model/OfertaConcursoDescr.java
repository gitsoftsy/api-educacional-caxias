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
@Table(name = "TBL_OFERTA_CONCURSO_DESCR")
@Data
public class OfertaConcursoDescr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_OFERTA_CONCURSO_DESCR")
    private Long idOfertaConcursoDescr;

    @ManyToOne
    @JoinColumn(name = "ID_CONTA", nullable = false)
    private Conta conta;
    
    @ManyToOne
    @JoinColumn(name = "ID_OFERTA_CONCURSO", nullable = false)
    private OfertaConcurso ofertaConcurso;
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

    @Column(name = "TITULO", length = 255)
    private String titulo;

    @Column(name = "DESCRICAO", length = 5000)
    private String descricao;

    @Column(name = "ORDEM", nullable = false)
    private Integer ordem;
	
}
