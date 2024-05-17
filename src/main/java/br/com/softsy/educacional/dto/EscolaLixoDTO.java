package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaLixo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaLixoDTO {

    private Long idEscolaLixo;
    @NotNull
    private Long escolaId;
    @NotNull
    private Character coletaPeriodica;
    @NotNull
    private Character queimaLixo;
    @NotNull
    private Character jogaOutraArea;
    @NotNull
    private Character reciclagem;
    @NotNull
    private Character enterra;
    @NotNull
    private Character outros;
    private String descricaoOutros;

    public EscolaLixoDTO(EscolaLixo escolaLixo) {
        this.idEscolaLixo = escolaLixo.getIdEscolaLixo();
        this.escolaId = escolaLixo.getEscola().getIdEscola();
        this.coletaPeriodica = escolaLixo.getColetaPeriodica();
        this.queimaLixo = escolaLixo.getQueimaLixo();
        this.jogaOutraArea = escolaLixo.getJogaOutraArea();
        this.reciclagem = escolaLixo.getReciclagem();
        this.enterra = escolaLixo.getEnterra();
        this.outros = escolaLixo.getOutros();
        this.descricaoOutros = escolaLixo.getDescricaoOutros();
    }
}