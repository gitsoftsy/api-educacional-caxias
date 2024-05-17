package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaDispositivo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaDispositivoDTO {

    private Long idEscolaDispositivo;
    @NotNull
    private Long escolaId;
    @NotNull
    private Integer qtdComputadoresAlunos;
    @NotNull
    private Integer qtdAparelhosDvd;
    @NotNull
    private Integer qtdImpressora;
    @NotNull
    private Integer qtdParabolicas;
    @NotNull
    private Integer qtdCopiadoras;
    @NotNull
    private Integer qtdProjetores;
    @NotNull
    private Integer qtdTvs;

    public EscolaDispositivoDTO(EscolaDispositivo escolaDispositivo) {
        this.idEscolaDispositivo = escolaDispositivo.getIdEscolaDispositivo();
        this.escolaId = escolaDispositivo.getEscola().getIdEscola();
        this.qtdComputadoresAlunos = escolaDispositivo.getQtdComputadoresAlunos();
        this.qtdAparelhosDvd = escolaDispositivo.getQtdAparelhosDvd();
        this.qtdImpressora = escolaDispositivo.getQtdImpressora();
        this.qtdParabolicas = escolaDispositivo.getQtdParabolicas();
        this.qtdCopiadoras = escolaDispositivo.getQtdCopiadoras();
        this.qtdProjetores = escolaDispositivo.getQtdProjetores();
        this.qtdTvs = escolaDispositivo.getQtdTvs();
    }
}