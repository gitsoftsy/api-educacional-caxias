package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.MarcaEquipamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarcaEquipamentoDTO {

    private Long idMarcaEquipamento;

	@NotNull
	private Long dependenciaAdmId;
	
    @NotNull
    private String marcaEquipamento;
    private LocalDateTime dataCadastro;

    private Character ativo;

    public MarcaEquipamentoDTO(MarcaEquipamento marcaEquipamento) {
        this.marcaEquipamento = marcaEquipamento.getMarcaEquipamento();
        this.idMarcaEquipamento = marcaEquipamento.getIdMarcaEquipamento();
        this.dataCadastro = marcaEquipamento.getDataCadastro();
        this.ativo = marcaEquipamento.getAtivo();
        this.dependenciaAdmId = marcaEquipamento.getDependenciaAdm().getIdDependenciaAdministrativa();
    }
}