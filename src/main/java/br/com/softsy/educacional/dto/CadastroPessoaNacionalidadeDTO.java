package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.PessoaNacionalidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroPessoaNacionalidadeDTO {
	
    private Long idPessoaNacionalidade;
    private Long pessoaId;
    private Long nacionalidadeId;
    
    public CadastroPessoaNacionalidadeDTO(PessoaNacionalidade pessoaNacionalidade) {
    	this.idPessoaNacionalidade = pessoaNacionalidade.getIdPessoaNacionalidade();
    	this.pessoaId = pessoaNacionalidade.getPessoa().getIdPessoa();
    	this.nacionalidadeId =pessoaNacionalidade.getNacionalidade().getIdNacionalidade();
    }

}
