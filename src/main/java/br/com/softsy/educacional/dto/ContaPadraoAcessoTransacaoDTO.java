package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ContaPadraoAcessoTransacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContaPadraoAcessoTransacaoDTO {

    private Long idContaPadraoAcessoTransacao;

    @NotNull
    private Long contaPadraoAcessoId;

    @NotNull
    private Long transacaoId;

    @NotNull
    private Character acessa;

    @NotNull
    private Character altera;

    public ContaPadraoAcessoTransacaoDTO(ContaPadraoAcessoTransacao contaPadraoAcessoTransacao) {
        this.idContaPadraoAcessoTransacao = contaPadraoAcessoTransacao.getIdContaPadraoAcessoTransacao();
        this.contaPadraoAcessoId = contaPadraoAcessoTransacao.getContaPadraoAcesso().getIdContaPadraoAcesso();
        this.transacaoId = contaPadraoAcessoTransacao.getTransacao().getIdTransacao();
        this.acessa = contaPadraoAcessoTransacao.getAcessa();
        this.altera = contaPadraoAcessoTransacao.getAltera();
    }
}