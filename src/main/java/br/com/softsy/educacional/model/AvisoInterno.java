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
@Table(name = "TBL_AVISO_INTERNO")
@Data
public class AvisoInterno {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AVISO_INTERNO")
    private Long idAvisoInterno;

    @Column(name = "ID_USUARIO_ENVIO")
    private Long idUsuarioEnvio;

    @Column(name = "ID_PROFESSOR_ENVIO")
    private Long idProfessorEnvio;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_AVISO", nullable = false)
    private TipoAviso tipoAviso;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

    @Column(name = "DTINI")
    private LocalDateTime dataInicio;

    @Column(name = "DTFIM")
    private LocalDateTime dataFim;

    @Column(name = "TITULO", length = 255, nullable = false)
    private String titulo;

    @Column(name = "MENSAGEM")
    private String mensagem;

    @Column(name = "ID_USUARIO_RECEBIMENTO")
    private Long idUsuarioRecebimento;

    @Column(name = "ID_PROFESSOR_RECEBIMENTO")
    private Long idProfessorRecebimento;

    @Column(name = "DT_LEITURA")
    private LocalDateTime dataLeitura;

    @Column(name = "PATH_ANEXO", length = 255)
    private String pathAnexo;

    @Column(name = "RESPOSTAS_ABERTAS", length = 1)
    private Character respostasAbertas;

}
