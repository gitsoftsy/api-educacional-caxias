package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.EscolaEquipamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaEquipamentoDTO {

    private Long idEscolaEquipamento;
    private Long escolaId;
    private EquipamentoDTO equipamento;
    private Integer quantidade;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public EscolaEquipamentoDTO(EscolaEquipamento escolaEquipamento) {
        this.idEscolaEquipamento = escolaEquipamento.getIdEscolaEquipamento();
        this.escolaId = escolaEquipamento.getEscola().getIdEscola();
        this.equipamento = new EquipamentoDTO(escolaEquipamento.getEquipamento());
        this.quantidade = escolaEquipamento.getQuantidade();
        this.dataCadastro = escolaEquipamento.getDataCadastro();
        this.ativo = escolaEquipamento.getAtivo();
    }
}
