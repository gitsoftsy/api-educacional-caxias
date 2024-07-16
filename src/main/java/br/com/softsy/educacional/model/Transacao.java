package br.com.softsy.educacional.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "TBL_TRANSACAO")
@Data
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACAO")
    private Long idTransacao;
    
    @ManyToOne
    @JoinColumn(name = "ID_MODULO", nullable = false)
    private Modulo modulo;
    
    @Column(name = "ID_COD_HTML", length = 80)
    private String idCodHtml;
    
    @Column(name = "URL", length = 255)
    private String url;
    
    @Column(name = "NOME", length = 255)
    private String nome;
    
    @Column(name = "EXIBE_MENU")
    private Character exibeMenu;
    
    @JsonIgnore
	@OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL)
	private Set<ContaPadraoAcessoTransacao> contaPadraoAcessoTransacao = new HashSet<>();
}