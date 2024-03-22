package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoAtendimento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoAtendimentoDTO {

    private Long idTipoAtendimento;

    @NotNull
    private String tipoAtendimento;

    private LocalDateTime dataCadastro;


    public TipoAtendimentoDTO(TipoAtendimento tipoAtendimento) {
        this.idTipoAtendimento = tipoAtendimento.getIdTipoAtendimento();
        this.tipoAtendimento = tipoAtendimento.getTipoAtendimento();
        this.dataCadastro = tipoAtendimento.getDataCadastro();
    }
}
