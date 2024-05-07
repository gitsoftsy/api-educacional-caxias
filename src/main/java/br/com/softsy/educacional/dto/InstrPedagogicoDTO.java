package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.InstrPedagogico;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstrPedagogicoDTO {

    private Long idInstrPedagogico;
    
	@NotNull
	private Long contaId;

    @NotNull
    private String instrPedagogico;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public InstrPedagogicoDTO(InstrPedagogico instrPedagogico) {
        this.instrPedagogico = instrPedagogico.getInstrPedagogico();
        this.idInstrPedagogico = instrPedagogico.getIdInstrPedagogica();
        this.dataCadastro = instrPedagogico.getDataCadastro();
        this.ativo = instrPedagogico.getAtivo();
        this.contaId = instrPedagogico.getConta().getIdConta();
    }
    
}