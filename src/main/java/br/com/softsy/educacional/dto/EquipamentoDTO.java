package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Equipamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipamentoDTO {

    private Long idEquipamento;

	private ContaDTO conta;
	
    private String equipamento;

    private MarcaEquipamentoDTO marcaEquipamentoId;

    private LocalDateTime dataCadastro;

    private Character ativo;

    public EquipamentoDTO(Equipamento equipamento) {
        this.idEquipamento = equipamento.getIdEquipamento();
        this.equipamento = equipamento.getEquipamento();
        this.marcaEquipamentoId = new MarcaEquipamentoDTO(equipamento.getMarcaEquipamento());
        this.dataCadastro = equipamento.getDataCadastro();
        this.ativo = equipamento.getAtivo();
        this.conta = new ContaDTO(equipamento.getConta());
    }
}