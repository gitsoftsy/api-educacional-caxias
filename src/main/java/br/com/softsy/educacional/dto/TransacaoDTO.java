package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Transacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransacaoDTO {

    private Long idTransacao;
    
    @NotNull
    private Long moduloId;

    @NotNull
    private String idCodHtml;
    
    private String url;
    
    private String nome;

    public TransacaoDTO(Transacao transacao) {
        this.idTransacao = transacao.getIdTransacao();
        this.moduloId = transacao.getModulo().getIdModulo();
        this.idCodHtml = transacao.getIdCodHtml();
        this.url = transacao.getUrl();
        this.nome = transacao.getNome();
    }
}