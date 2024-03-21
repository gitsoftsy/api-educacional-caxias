package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.AnoEscolar;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnoEscolarDTO {

    private Long idAnoEscolar;

    private String anoEscolar;

    private LocalDateTime dataCadastro;

    public AnoEscolarDTO(AnoEscolar anoEscolar) {
        this.idAnoEscolar = anoEscolar.getIdAnoEscolar();
        this.anoEscolar = anoEscolar.getAnoEscolar();
        this.dataCadastro = anoEscolar.getDataCadastro();
    }
}