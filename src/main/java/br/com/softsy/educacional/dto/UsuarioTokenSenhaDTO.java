package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.UsuarioTokenSenha;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioTokenSenhaDTO {

    private Long idUsuarioTokenSenha;

    private LocalDateTime dataCadastro;

    @NotNull
    private Character ativo;

    @NotNull
    private Long idUsuario; 

    private String token;

    @NotNull
    private LocalDateTime dataValidade;

    @NotNull
    private Character utilizado;

    public UsuarioTokenSenhaDTO(UsuarioTokenSenha usuarioTokenSenha) {
        this.idUsuarioTokenSenha = usuarioTokenSenha.getIdUsuarioTokenSenha();
        this.dataCadastro = usuarioTokenSenha.getDataCadastro();
        this.ativo = usuarioTokenSenha.getAtivo();
        this.idUsuario = usuarioTokenSenha.getUsuario().getIdUsuario(); // Assumindo que há um método `getIdUsuario` na entidade Usuario
        this.token = usuarioTokenSenha.getToken();
        this.dataValidade = usuarioTokenSenha.getDataValidade();
        this.utilizado = usuarioTokenSenha.getUtilizado();
    }
}
