package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.FeriadoEscola;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeriadoEscolaDTO {

    private Long idFeriadoEscola;

    private String descricao;

    @NotNull
    private Long escolaId;

    @NotNull
    private LocalDate dataFeriado;

    private LocalDateTime dataCadastro;

    private Character ativo;

    public FeriadoEscolaDTO(FeriadoEscola feriadoConta) {
        this.idFeriadoEscola = feriadoConta.getIdFeriadoEscola();
        this.descricao = feriadoConta.getDescricao();
        this.escolaId = feriadoConta.getEscola().getIdEscola();
        this.dataFeriado = feriadoConta.getDataFeriado();
        this.dataCadastro = feriadoConta.getDataCadastro();
        this.ativo = feriadoConta.getAtivo();
    }
	
}
