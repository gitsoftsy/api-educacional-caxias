package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.UsuarioConta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioContaDTO {

    private Long idUsuarioConta;

    @NotNull
    private Long usuarioId;

    private Long contaPadraoAcessoId;
    
    private Long escolaId;

    public UsuarioContaDTO(UsuarioConta usuarioConta) {
        this.idUsuarioConta = usuarioConta.getIdUsuarioConta();
        this.usuarioId = usuarioConta.getUsuario().getIdUsuario();
        this.contaPadraoAcessoId = usuarioConta.getContaPadraoAcesso().getIdContaPadraoAcesso();
        this.escolaId = usuarioConta.getEscola().getIdEscola();
    }
}