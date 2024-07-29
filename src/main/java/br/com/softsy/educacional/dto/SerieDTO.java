package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Serie;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SerieDTO {
    
    private Long idSerie;

    @NotNull
    private String serie;

    @NotNull
    private String descricao;

    private LocalDateTime dataCadastro;

    private Character ativo;

    @NotNull
    private Long contaId;

    public SerieDTO(Serie serie) {
        this.idSerie = serie.getIdSerie();
        this.serie = serie.getSerie();
        this.descricao = serie.getDescricao();
        this.dataCadastro = serie.getDataCadastro();
        this.ativo = serie.getAtivo();
        this.contaId = serie.getConta().getIdConta();
    }
}