package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaEsgoto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaEsgotoDTO {

    private Long idEscolaEsgoto;

    @NotNull
    private Long escolaId;

    @NotNull
    private Character redePublica;

    @NotNull
    private Character fossa;

    @NotNull
    private Character inexistente;

    public EscolaEsgotoDTO(EscolaEsgoto escolaEsgoto) {
        this.idEscolaEsgoto = escolaEsgoto.getIdEscolaEsgoto();
        this.escolaId = escolaEsgoto.getEscola().getIdEscola();
        this.redePublica = escolaEsgoto.getRedePublica();
        this.fossa = escolaEsgoto.getFossa();
        this.inexistente = escolaEsgoto.getInexistente();
    }
}