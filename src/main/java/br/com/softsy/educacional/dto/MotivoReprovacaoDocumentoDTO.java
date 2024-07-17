package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.MotivoReprovacaoDocumento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MotivoReprovacaoDocumentoDTO {

    private Long idMotivoReprovacaoDocumento;

    @NotNull
    private Long contaId;

    @NotNull
    private String motivoReprovacaoDocumento;

    private LocalDateTime dataCadastro;

    private Character ativo;

    @NotNull
    private Character obrigatorio;

    public MotivoReprovacaoDocumentoDTO(MotivoReprovacaoDocumento motivoReprovacaoDocumento) {
        this.idMotivoReprovacaoDocumento = motivoReprovacaoDocumento.getIdMotivoReprovacaoDocumento();
        this.contaId = motivoReprovacaoDocumento.getConta().getIdConta();
        this.motivoReprovacaoDocumento = motivoReprovacaoDocumento.getMotivoReprovacaoDocumento();
        this.dataCadastro = motivoReprovacaoDocumento.getDataCadastro();
        this.ativo = motivoReprovacaoDocumento.getAtivo();
        this.obrigatorio = motivoReprovacaoDocumento.getObrigatorio();
    }
}	