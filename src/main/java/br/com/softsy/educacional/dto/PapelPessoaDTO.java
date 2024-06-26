package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

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
    
	private LocalDateTime dataCadastro;
	
	private Character ativo;

    public PapelPessoaDTO(PapelPessoa papelPessoa) {
        this.idPapelPessoa = papelPessoa.getIdPapelPessoa();
        this.contaId = papelPessoa.getConta().getIdConta();  
        this.papelPessoa = papelPessoa.getPapelPessoa();
        this.dataCadastro = papelPessoa.getDataCadastro();
        this.ativo = papelPessoa.getAtivo();
    }
}