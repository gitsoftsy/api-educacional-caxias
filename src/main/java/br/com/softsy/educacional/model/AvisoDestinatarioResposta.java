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
@Table(name = "TBL_AVISO_DESTINATARIO_RESPOSTA")
@Data
public class AvisoDestinatarioResposta {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AVISO_DESTINATARIO_RESPOSTA")
    private Long idAvisoDestinatarioResposta;
    
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
    
    @ManyToOne
    @JoinColumn(name = "ID_AVISO_DESTINATARIO", nullable = false)
    private AvisoDestinatario avisoDestinatario;
    
    @Column(name = "MENSAGEM", nullable = false)
    private String mensagem;
    
    @Column(name = "DT_LEITURA")
    private LocalDateTime dataLeitura;
    
    @Column(name = "PATH_ANEXO", length = 255)
    private String pathAnexo;

}
