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
@Table(name = "TBL_AVISO_INTERNO_RESPOSTA")
@Data
public class AvisoInternoResposta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AVISO_INTERNO_RESPOSTA")
    private Long idAvisoInternoResposta;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ID_AVISO_INTERNO")
    private AvisoInterno avisoInterno;
	
	 @ManyToOne
	 @JoinColumn(name = "ID_USUARIO")
	 private Usuario usuario;

	 @ManyToOne
	 @JoinColumn(name = "ID_PROFESSOR")
	 private Professor professor;

	 @Column(name = "MENSAGEM")
	 private String mensagem;

	  @Column(name = "PATH_ANEXO", length = 255)
	  private String pathAnexo;
	
	
	
}
