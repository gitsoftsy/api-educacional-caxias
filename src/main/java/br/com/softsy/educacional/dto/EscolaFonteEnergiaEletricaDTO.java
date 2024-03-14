package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaFonteEnergiaEletrica;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaFonteEnergiaEletricaDTO {

    private Long idEscolaFonteEnergiaEletrica;
    private Long escolaId;
    private FonteEnergiaEletricaDTO fonteEnergiaEletrica;

    public EscolaFonteEnergiaEletricaDTO(EscolaFonteEnergiaEletrica escolaFonteEnergiaEletrica) {
        this.idEscolaFonteEnergiaEletrica = escolaFonteEnergiaEletrica.getIdEscolaFonteEnergiaEletrica();
        this.escolaId = escolaFonteEnergiaEletrica.getEscola().getIdEscola();
        this.fonteEnergiaEletrica = new FonteEnergiaEletricaDTO(escolaFonteEnergiaEletrica.getFonteEnergiaEletrica());
    }
}
