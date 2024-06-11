package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoIngresso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoIngressoDTO {

    private Long idTipoIngresso;

    @NotNull
    private Long contaId;

    @NotNull
    private String tipoIngresso;

    private Character ativo;

    private LocalDateTime dataCadastro;

    public TipoIngressoDTO(TipoIngresso tipoIngresso) {
        this.idTipoIngresso = tipoIngresso.getIdTipoIngresso();
        this.contaId = tipoIngresso.getConta().getIdConta();  
        this.tipoIngresso = tipoIngresso.getTipoIngresso();
        this.ativo = tipoIngresso.getAtivo();
        this.dataCadastro = tipoIngresso.getDataCadastro();
    }
}