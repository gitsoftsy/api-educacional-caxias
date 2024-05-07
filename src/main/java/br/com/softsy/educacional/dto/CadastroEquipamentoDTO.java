package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Equipamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEquipamentoDTO {

    private Long idEquipamento;

	@NotNull
	private Long contaId;
	
    private String equipamento;

    private Long marcaEquipamentoId;

    private LocalDateTime dataCadastro;

    private Character ativo;

    public CadastroEquipamentoDTO(Equipamento equipamento) {
        this.idEquipamento = equipamento.getIdEquipamento();
        this.equipamento = equipamento.getEquipamento();
        this.marcaEquipamentoId = equipamento.getMarcaEquipamento().getIdMarcaEquipamento();
        this.dataCadastro = equipamento.getDataCadastro();
        this.ativo = equipamento.getAtivo();
        this.contaId = equipamento.getConta().getIdConta();
    }
}