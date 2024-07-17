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
@Table(name = "TBL_CANDIDATO_DOCUMENTO_INGRESSO")
@Data
public class CandidatoDocumentoIngresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CANDIDATO_DOCUMENTO_INGRESSO")
    private Long idCandidatoDocumentoIngresso;

    @ManyToOne
    @JoinColumn(name = "ID_CANDIDATO", nullable = false)
    private Candidato candidato;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "DOC_FILE_SERVER")
    private String docFileServer;

    @Column(name = "DOC_APROVADO", nullable = false)
    private Character docAprovado;

    @Column(name = "DT_APROVACAO")
    private LocalDateTime dataAprovacao;

    @ManyToOne
    @JoinColumn(name = "ID_MOTIVO_REPROVACAO_DOCUMENTO")
    private MotivoReprovacaoDocumento motivoReprovacaoDocumento;

    @Column(name = "OBS_APROVACAO", length = 2500)
    private String obsAprovacao;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO_APROVACAO")
    private Usuario usuarioAprovacao;
}