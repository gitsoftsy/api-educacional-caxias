package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.ComponentesCurriculares;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentesCurricularesDTO {

	private Long idComponentesCurriculares;

    private String componentesCurriculares;

    private LocalDateTime dataCadastro;
    
    public ComponentesCurricularesDTO(ComponentesCurriculares componentesCurriculares) {
        this.idComponentesCurriculares = componentesCurriculares.getIdComponentesCurriculares();
        this.componentesCurriculares = componentesCurriculares.getComponentesCurriculares();
        this.dataCadastro = componentesCurriculares.getDataCadastro();
    }
}
