package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ContaPadraoAcesso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContaPadraoAcessoDTO {

    private Long idContaPadraoAcesso;

    @NotNull
    private Long contaId;

    private Character ativo;

    @NotNull
    private String padraoAcesso;

    private LocalDateTime dtCadastro;

    public ContaPadraoAcessoDTO(ContaPadraoAcesso contaPadraoAcesso) {
        this.idContaPadraoAcesso = contaPadraoAcesso.getIdContaPadraoAcesso();
        this.contaId = contaPadraoAcesso.getConta().getIdConta();
        this.ativo = contaPadraoAcesso.getAtivo();
        this.padraoAcesso = contaPadraoAcesso.getPadraoAcesso();
        this.dtCadastro = contaPadraoAcesso.getDtCadastro();
    }
}