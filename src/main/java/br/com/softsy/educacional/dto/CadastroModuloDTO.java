package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.softsy.educacional.model.Modulo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroModuloDTO {

    private Long idModulo;

    @NotNull
    private String modulo;

    private String icone;

    public CadastroModuloDTO(Modulo modulo) {
        this.idModulo = modulo.getIdModulo();
        this.modulo = modulo.getModulo();
        this.icone = modulo.getIcone();
    }
}
