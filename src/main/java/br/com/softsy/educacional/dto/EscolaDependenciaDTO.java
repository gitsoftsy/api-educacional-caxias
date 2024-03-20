package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaDependencia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaDependenciaDTO {

    private Long idEscolaDependencia;
    private Long escolaId;
    private String dependencia;
    private Character acessivel;
    private Character internaExterna;
    private Character climatizada;
    private Integer quantidade;
    private TipoDependenciaDTO tipoDependencia;

    public EscolaDependenciaDTO(EscolaDependencia escolaDependencia) {
        this.idEscolaDependencia = escolaDependencia.getIdEscolaDependencia();
        this.escolaId = escolaDependencia.getEscola().getIdEscola();
        this.dependencia = escolaDependencia.getDependencia();
        this.acessivel = escolaDependencia.getAcessivel();
        this.internaExterna = escolaDependencia.getInternaExterna();
        this.climatizada = escolaDependencia.getClimatizada();
        this.quantidade = escolaDependencia.getQuantidade();
        this.tipoDependencia = new TipoDependenciaDTO(escolaDependencia.getTipoDependencia());
    }
}
