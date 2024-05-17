package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaAgua;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaAguaDTO {

    private Long idEscolaAgua;
    private Long escolaId;
    private Character aguaTratada;
    private Character aguaPocoArtesiano;
    private Character aguaCacimba;
    private Character aguaFonteRio;
    private Character semAgua;

    public EscolaAguaDTO(EscolaAgua escolaAgua) {
        this.idEscolaAgua = escolaAgua.getIdEscolaAgua();
        this.escolaId = escolaAgua.getEscola().getIdEscola();
        this.aguaTratada = escolaAgua.getAguaTratada();
        this.aguaPocoArtesiano = escolaAgua.getAguaPocoArtesiano();
        this.aguaCacimba = escolaAgua.getAguaCacimba();
        this.aguaFonteRio = escolaAgua.getAguaFonteRio();
        this.semAgua = escolaAgua.getSemAgua();
    }
}