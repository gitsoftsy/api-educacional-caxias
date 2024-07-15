package br.com.softsy.educacional.model;

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
@Table(name = "TBL_USUARIO_CONTA")
@Data
public class UsuarioConta {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO_CONTA")
    private Long idUsuarioConta;
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_CONTA_PADRAO_ACESSO")
    private ContaPadraoAcesso contaPadraoAcesso;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESCOLA")
    private Escola escola;

}
