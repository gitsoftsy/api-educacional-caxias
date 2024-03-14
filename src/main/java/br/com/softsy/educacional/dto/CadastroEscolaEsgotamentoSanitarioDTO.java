package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaEsgotamentoSanitario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaEsgotamentoSanitarioDTO {

    private Long idEscolaEsgotamentoSanitario;
    @NotNull
    private Long escolaId;
    @NotNull
    private Long esgotamentoSanitarioId;

    public CadastroEscolaEsgotamentoSanitarioDTO(EscolaEsgotamentoSanitario escolaEsgotamentoSanitario) {
        this.idEscolaEsgotamentoSanitario = escolaEsgotamentoSanitario.getIdEscolaEsgotamentoSanitario();
        this.escolaId = escolaEsgotamentoSanitario.getEscola().getIdEscola();
        this.esgotamentoSanitarioId = escolaEsgotamentoSanitario.getEsgotamentoSanitario().getIdEsgotamentoSanitario();
    }
}
