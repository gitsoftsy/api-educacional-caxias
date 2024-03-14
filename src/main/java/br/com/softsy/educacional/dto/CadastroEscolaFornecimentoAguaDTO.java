package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaFornecimentoAgua;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaFornecimentoAguaDTO {

    private Long idEscolaFornecimentoAgua;
    @NotNull
    private Long escolaId;
    @NotNull
    private Long fornecimentoAguaId;

    public CadastroEscolaFornecimentoAguaDTO(EscolaFornecimentoAgua escolaFornecimentoAgua) {
        this.idEscolaFornecimentoAgua = escolaFornecimentoAgua.getIdEscolaFornecimentoAgua();
        this.escolaId = escolaFornecimentoAgua.getEscola().getIdEscola();
        this.fornecimentoAguaId = escolaFornecimentoAgua.getFornecimentoAgua().getIdFornecimentoAgua();
    }
}