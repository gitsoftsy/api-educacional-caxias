package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaEsgotamentoSanitario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaEsgotamentoSanitarioDTO {

    private Long idEscolaEsgotamentoSanitario;
    private Long escolaId;
    private EsgotamentoSanitarioDTO esgotamentoSanitario;

    public EscolaEsgotamentoSanitarioDTO(EscolaEsgotamentoSanitario escolaEsgotamentoSanitario) {
        this.idEscolaEsgotamentoSanitario = escolaEsgotamentoSanitario.getIdEscolaEsgotamentoSanitario();
        this.escolaId = escolaEsgotamentoSanitario.getEscola().getIdEscola();
        this.esgotamentoSanitario = new EsgotamentoSanitarioDTO(escolaEsgotamentoSanitario.getEsgotamentoSanitario());
    }
}
