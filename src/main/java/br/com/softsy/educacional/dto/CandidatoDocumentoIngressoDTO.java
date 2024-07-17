package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.CandidatoDocumentoIngresso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidatoDocumentoIngressoDTO {

    private Long idCandidatoDocumentoIngresso;

    @NotNull
    private Long candidatoId;

    private LocalDateTime dataCadastro;

    private String docFileServer;

    @NotNull
    private Character docAprovado;

    private LocalDateTime dataAprovacao;

    private Long motivoReprovacaoDocumentoId;

    private String obsAprovacao;

    private Long usuarioAprovacaoId;

    public CandidatoDocumentoIngressoDTO(CandidatoDocumentoIngresso candidatoDocumentoIngresso) {
        this.idCandidatoDocumentoIngresso = candidatoDocumentoIngresso.getIdCandidatoDocumentoIngresso();
        this.candidatoId = candidatoDocumentoIngresso.getCandidato().getIdCandidato();
        this.dataCadastro = candidatoDocumentoIngresso.getDataCadastro();
        this.docFileServer = candidatoDocumentoIngresso.getDocFileServer();
        this.docAprovado = candidatoDocumentoIngresso.getDocAprovado();
        this.dataAprovacao = candidatoDocumentoIngresso.getDataAprovacao();
        this.motivoReprovacaoDocumentoId = candidatoDocumentoIngresso.getMotivoReprovacaoDocumento().getIdMotivoReprovacaoDocumento();
        this.obsAprovacao = candidatoDocumentoIngresso.getObsAprovacao();
        this.usuarioAprovacaoId = candidatoDocumentoIngresso.getUsuarioAprovacao() != null ?
            candidatoDocumentoIngresso.getUsuarioAprovacao().getIdUsuario() : null;
    }
}