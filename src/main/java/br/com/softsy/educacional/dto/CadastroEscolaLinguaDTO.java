package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaLingua;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaLinguaDTO {

    private Long idEscolaLingua;
    @NotNull
    private Long escolaId;
    @NotNull
    private Long linguaEnsinoId;
    
    public CadastroEscolaLinguaDTO(EscolaLingua escolaLingua) {
        this.idEscolaLingua = escolaLingua.getIdEscolaLingua();
        this.escolaId = escolaLingua.getEscola().getIdEscola();
        this.linguaEnsinoId = escolaLingua.getLinguaEnsino().getIdLinguaEnsino();
    }
}
