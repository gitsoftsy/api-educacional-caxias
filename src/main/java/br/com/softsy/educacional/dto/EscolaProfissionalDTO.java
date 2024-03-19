package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaProfissional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaProfissionalDTO {

    private Long idEscolaProfissional;
    private Long escolaId;
    private TipoProfissionalDTO tipoProfissional;
    private Integer quantidade;

    public EscolaProfissionalDTO(EscolaProfissional escolaProfissional) {
        this.idEscolaProfissional = escolaProfissional.getIdEscolaProfissional();
        this.escolaId = escolaProfissional.getEscola().getIdEscola();
        this.tipoProfissional = new TipoProfissionalDTO(escolaProfissional.getTipoProfissional());
        this.quantidade = escolaProfissional.getQuantidade();
    }
}