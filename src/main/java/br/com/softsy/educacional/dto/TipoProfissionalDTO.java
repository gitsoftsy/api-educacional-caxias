package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoProfissional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoProfissionalDTO {

    private Long idTipoProfissional;
    
	@NotNull
	private Long dependenciaAdmId;

    @NotNull
    private String tipoProfissional;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public TipoProfissionalDTO(TipoProfissional tipoProfissional) {
        this.tipoProfissional = tipoProfissional.getTipoProfissional();
        this.idTipoProfissional = tipoProfissional.getIdTipoProfissional();
        this.dataCadastro = tipoProfissional.getDataCadastro();
        this.ativo = tipoProfissional.getAtivo();
        this.dependenciaAdmId = tipoProfissional.getDependenciaAdm().getIdDependenciaAdministrativa();
    }
}