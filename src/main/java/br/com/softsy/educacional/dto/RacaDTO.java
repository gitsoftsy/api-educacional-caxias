package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.controller.Raca;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RacaDTO {

    private Long idRaca;

    @NotNull
    private String raca;

    private LocalDateTime dataCadastro;

    private Character ativo;

    public RacaDTO(Raca raca) {
        this.idRaca = raca.getIdRaca();
        this.raca = raca.getRaca();
        this.dataCadastro = raca.getDataCadastro();
        this.ativo = raca.getAtivo();
    }

}