package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.EscolaInstrPedagogico;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaInstrPedagogicoDTO {

    private Long idEscolaInstrPedagogico;
    private Long escolaId;
    private InstrPedagogicoDTO instrPedagogico;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public EscolaInstrPedagogicoDTO(EscolaInstrPedagogico escolaInstrPedagogico) {
        this.idEscolaInstrPedagogico = escolaInstrPedagogico.getIdEscolaInstrPedagogico();
        this.escolaId = escolaInstrPedagogico.getEscola().getIdEscola();
        this.instrPedagogico = new InstrPedagogicoDTO(escolaInstrPedagogico.getInstrPedagogico());
        this.dataCadastro = escolaInstrPedagogico.getDataCadastro();
        this.ativo = escolaInstrPedagogico.getAtivo();
    }
}