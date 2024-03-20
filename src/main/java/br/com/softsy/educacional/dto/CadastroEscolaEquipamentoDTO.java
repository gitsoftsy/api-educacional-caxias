package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaEquipamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaEquipamentoDTO {

    private Long idEscolaEquipamento;
    @NotNull
    private Long escolaId;
    @NotNull
    private Long equipamentoId;
    @NotNull
    private Integer quantidade;
    
    private LocalDateTime dataCadastro;
    private Character ativo;

    public CadastroEscolaEquipamentoDTO(EscolaEquipamento escolaEquipamento) {
        this.idEscolaEquipamento = escolaEquipamento.getIdEscolaEquipamento();
        this.escolaId = escolaEquipamento.getEscola().getIdEscola();
        this.equipamentoId = escolaEquipamento.getEquipamento().getIdEquipamento();
        this.quantidade = escolaEquipamento.getQuantidade();
        this.dataCadastro = escolaEquipamento.getDataCadastro();
        this.ativo = escolaEquipamento.getAtivo();
    }
}