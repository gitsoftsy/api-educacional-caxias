package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaEnergiaEletrica;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaEnergiaEletricaDTO {

    private Long idEscolaEnergiaEletrica;
    @NotNull
    private Long escolaId;
    @NotNull
    private Character redePublica;
    @NotNull
    private Character gerador;
    @NotNull
    private Character outros;
    private String descricaoOutros;
    @NotNull
    private Character semEnergiaEletrica;

    public EscolaEnergiaEletricaDTO(EscolaEnergiaEletrica escolaEnergiaEletrica) {
        this.idEscolaEnergiaEletrica = escolaEnergiaEletrica.getIdEscolaEnergiaEletrica();
        this.escolaId = escolaEnergiaEletrica.getEscola().getIdEscola();
        this.redePublica = escolaEnergiaEletrica.getRedePublica();
        this.gerador = escolaEnergiaEletrica.getGerador();
        this.outros = escolaEnergiaEletrica.getOutros();
        this.descricaoOutros = escolaEnergiaEletrica.getDescricaoOutros();
        this.semEnergiaEletrica = escolaEnergiaEletrica.getSemEnergiaEletrica();
    }
}