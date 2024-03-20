package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaInstrPedagogico;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaInstrPedagogicoDTO {

    private Long idEscolaInstrPedagogico;
    @NotNull
    private Long escolaId;
    @NotNull
    private Long instrPedagogicoId;
    
    private LocalDateTime dataCadastro;
    private Character ativo;

    public CadastroEscolaInstrPedagogicoDTO(EscolaInstrPedagogico escolaInstrPedagogico) {
        this.idEscolaInstrPedagogico = escolaInstrPedagogico.getIdEscolaInstrPedagogico();
        this.escolaId = escolaInstrPedagogico.getEscola().getIdEscola();
        this.instrPedagogicoId = escolaInstrPedagogico.getInstrPedagogico().getIdInstrPedagogica();
        this.dataCadastro = escolaInstrPedagogico.getDataCadastro();
        this.ativo = escolaInstrPedagogico.getAtivo();
    }
}
