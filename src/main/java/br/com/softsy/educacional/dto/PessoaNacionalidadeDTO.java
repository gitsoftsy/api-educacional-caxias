package br.com.softsy.educacional.dto;
import br.com.softsy.educacional.model.PessoaNacionalidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaNacionalidadeDTO {
	
    private Long idPessoaNacionalidade;
    private Long pessoaId;
    private NacionalidadeDTO nacionalidade;
    
    public PessoaNacionalidadeDTO(PessoaNacionalidade pessoaNacionalidade) {
    	this.idPessoaNacionalidade = pessoaNacionalidade.getIdPessoaNacionalidade();
    	this.pessoaId = pessoaNacionalidade.getPessoa().getIdPessoa();
    	this.nacionalidade = new NacionalidadeDTO(pessoaNacionalidade.getNacionalidade());
    }

}
