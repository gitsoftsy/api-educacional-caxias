package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaProfissional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaProfissionalDTO {

    private Long idEscolaProfissional;
    private Long escolaId;
    private Long tipoProfissionalId;
    private Integer quantidade;

    public CadastroEscolaProfissionalDTO(EscolaProfissional escolaProfissional) {
        this.idEscolaProfissional = escolaProfissional.getIdEscolaProfissional();
        this.escolaId = escolaProfissional.getEscola().getIdEscola();
        this.tipoProfissionalId = escolaProfissional.getTipoProfissional().getIdTipoProfissional();
        this.quantidade = escolaProfissional.getQuantidade();
    }
}