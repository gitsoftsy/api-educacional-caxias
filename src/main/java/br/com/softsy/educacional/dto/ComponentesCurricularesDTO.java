package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ComponentesCurriculares;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentesCurricularesDTO {

	private Long idComponentesCurriculares;
	
	@NotNull
	private Long dependenciaAdmId;

    private String componentesCurriculares;

    private LocalDateTime dataCadastro;
    
    public ComponentesCurricularesDTO(ComponentesCurriculares componentesCurriculares) {
        this.idComponentesCurriculares = componentesCurriculares.getIdComponentesCurriculares();
        this.componentesCurriculares = componentesCurriculares.getComponentesCurriculares();
        this.dataCadastro = componentesCurriculares.getDataCadastro();
        this.dependenciaAdmId = componentesCurriculares.getDependenciaAdm().getIdDependenciaAdministrativa();
    }
}
