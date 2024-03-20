package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaDependencia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaDependenciaDTO {

    private Long idEscolaDependencia;
    @NotNull
    private Long escolaId;
    @NotNull
    private String dependencia;
    private Character acessivel;
    private Character internaExterna;
    private Character climatizada;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Long tipoDependenciaId;

    public CadastroEscolaDependenciaDTO(EscolaDependencia escolaDependencia) {
        this.idEscolaDependencia = escolaDependencia.getIdEscolaDependencia();
        this.escolaId = escolaDependencia.getEscola().getIdEscola();
        this.dependencia = escolaDependencia.getDependencia();
        this.acessivel = escolaDependencia.getAcessivel();
        this.internaExterna = escolaDependencia.getInternaExterna();
        this.climatizada = escolaDependencia.getClimatizada();
        this.quantidade = escolaDependencia.getQuantidade();
        this.tipoDependenciaId = escolaDependencia.getTipoDependencia().getIdTipoDependencia();
    }
}
