package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.FeriadoConta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeriadoContaDTO {

    private Long idFeriadoConta;

    private String descricao;

    @NotNull
    private Long contaId;

    @NotNull
    private LocalDate dataFeriado;

    private LocalDateTime dataCadastro;

    private Character ativo;

    public FeriadoContaDTO(FeriadoConta feriadoConta) {
        this.idFeriadoConta = feriadoConta.getIdFeriadoConta();
        this.descricao = feriadoConta.getDescricao();
        this.contaId = feriadoConta.getConta().getIdConta();
        this.dataFeriado = feriadoConta.getDataFeriado();
        this.dataCadastro = feriadoConta.getDataCadastro();
        this.ativo = feriadoConta.getAtivo();
    }
}