package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Pais;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaisDTO {

    private Long idPais;

    @NotNull
    private String codPais;

    @NotNull
    private String nomePais;

    @NotNull
    private String codigoIso;

    public PaisDTO(Pais pais) {
        this.idPais = pais.getIdPais();
        this.codPais = pais.getCodPais();
        this.nomePais = pais.getNomePais();
        this.codigoIso = pais.getCodigoIso();
    }

}