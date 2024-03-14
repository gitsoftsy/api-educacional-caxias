package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaFornecimentoAgua;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaFornecimentoAguaDTO {

    private Long idEscolaFornecimentoAgua;
    private Long escolaId;
    private FornecimentoAguaDTO fornecimentoAgua;

    public EscolaFornecimentoAguaDTO(EscolaFornecimentoAgua escolaFornecimentoAgua) {
        this.idEscolaFornecimentoAgua = escolaFornecimentoAgua.getIdEscolaFornecimentoAgua();
        this.escolaId = escolaFornecimentoAgua.getEscola().getIdEscola();
        this.fornecimentoAgua = new FornecimentoAguaDTO(escolaFornecimentoAgua.getFornecimentoAgua());
    }
}
