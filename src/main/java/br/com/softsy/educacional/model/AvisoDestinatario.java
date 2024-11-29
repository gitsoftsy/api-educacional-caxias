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
@Table(name = "TBL_AVISO_DESTINATARIO")
@Data
public class AvisoDestinatario {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AVISO_DESTINATARIO")
    private Long idAvisoDestinatario;
    
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
    
    @ManyToOne
    @JoinColumn(name = "ID_AVISO", nullable = false)
    private Aviso aviso;
    
    @ManyToOne
    @JoinColumn(name = "ID_ALUNO", nullable = false)
    private Aluno aluno;
    
    @Column(name = "DT_LEITURA")
    private LocalDateTime dataLeitura;


}
