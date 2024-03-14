package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaLingua;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaLinguaDTO {

    private Long idEscolaLingua;
    private Long escola;
    private LinguaEnsinoDTO linguaEnsino;

    public EscolaLinguaDTO(EscolaLingua escolaLingua) {
        this.idEscolaLingua = escolaLingua.getIdEscolaLingua();
        this.escola = escolaLingua.getEscola().getIdEscola();
        this.linguaEnsino = new LinguaEnsinoDTO(escolaLingua.getLinguaEnsino());
    }
}
