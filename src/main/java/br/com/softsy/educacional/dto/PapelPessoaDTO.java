package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.PapelPessoa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PapelPessoaDTO {

    private Long idPapelPessoa;

    @NotNull
    private Long contaId;

    @NotNull
    private String papelPessoa;

    public PapelPessoaDTO(PapelPessoa tipoIngresso) {
        this.idPapelPessoa = tipoIngresso.getIdPapelPessoa();
        this.contaId = tipoIngresso.getConta().getIdConta();  
        this.papelPessoa = tipoIngresso.getPapelPessoa();
    }
}