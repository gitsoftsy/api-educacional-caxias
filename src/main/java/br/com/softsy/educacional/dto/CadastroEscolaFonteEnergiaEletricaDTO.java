package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaFonteEnergiaEletrica;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaFonteEnergiaEletricaDTO {

    private Long idEscolaFonteEnergiaEletrica;
    @NotNull
    private Long escolaId;
    @NotNull
    private Long fonteEnergiaEletricaId;

    public CadastroEscolaFonteEnergiaEletricaDTO(EscolaFonteEnergiaEletrica escolaFonteEnergiaEletrica) {
        this.idEscolaFonteEnergiaEletrica = escolaFonteEnergiaEletrica.getIdEscolaFonteEnergiaEletrica();
        this.escolaId = escolaFonteEnergiaEletrica.getEscola().getIdEscola();
        this.fonteEnergiaEletricaId = escolaFonteEnergiaEletrica.getFonteEnergiaEletrica().getIdFonteEnergiaEletrica();
    }
}
