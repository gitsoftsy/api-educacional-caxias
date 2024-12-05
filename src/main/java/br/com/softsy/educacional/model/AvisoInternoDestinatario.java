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
@Table(name = "TBL_AVISO_INTERNO_DESTINATARIO")
@Data
public class AvisoInternoDestinatario {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID_AVISO_INTERNO_DESTINATARIO")
	 private Long idAvisoInternoDestinatario;
	    
	 @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	 private LocalDateTime dataCadastro;
	    
	 @ManyToOne
	 @JoinColumn(name = "ID_AVISO_INTERNO", nullable = false)
	 private AvisoInterno avisoInterno;
	    
	 @ManyToOne
	 @JoinColumn(name = "ID_USUARIO_DESTINATARIO", nullable = true)
	 private Usuario usuarioDestinatario;
	 
	 @ManyToOne
	 @JoinColumn(name = "ID_PROFESSRO_DESTINATARIO", nullable = true)
	 private Professor professorDestinatario;

	 @Column(name = "DT_LEITURA")
	 private LocalDateTime dataLeitura;

	
	

}
