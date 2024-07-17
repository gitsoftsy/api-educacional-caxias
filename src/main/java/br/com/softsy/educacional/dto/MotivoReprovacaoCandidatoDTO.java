package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.MotivoReprovacaoCandidato;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MotivoReprovacaoCandidatoDTO {

    private Long idMotivoReprovacaoCandidato;

    @NotNull
    private Long contaId;

    @NotNull
    private String motivoReprovacaoCandidato;

    private LocalDateTime dataCadastro;

    private Character ativo;

    @NotNull
    private Character obrigatorio;

    public MotivoReprovacaoCandidatoDTO(MotivoReprovacaoCandidato motivoReprovacaoCandidato) {
        this.idMotivoReprovacaoCandidato = motivoReprovacaoCandidato.getIdMotivoReprovacaoCandidato();
        this.contaId = motivoReprovacaoCandidato.getConta().getIdConta();
        this.motivoReprovacaoCandidato = motivoReprovacaoCandidato.getMotivoReprovacaoCandidato();
        this.dataCadastro = motivoReprovacaoCandidato.getDataCadastro();
        this.ativo = motivoReprovacaoCandidato.getAtivo();
        this.obrigatorio = motivoReprovacaoCandidato.getObrigatorio();
    }
}