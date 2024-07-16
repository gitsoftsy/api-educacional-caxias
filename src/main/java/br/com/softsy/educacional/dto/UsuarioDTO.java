package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import br.com.softsy.educacional.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    private Long idUsuario;

    private LocalDateTime dataCadastro;

    @NotNull
    private String usuario;

    @NotNull
    private String nomeCompleto;

    @NotNull
    private String email;

    private Character emailVerificado;

    @NotNull
    @CPF
    private String cpf;

    private LocalDateTime dataNascimento;

    private String senha;

    private Character ativo;

    private String celular;

    private Character celularVerificado;

    public UsuarioDTO(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.dataCadastro = usuario.getDataCadastro();
        this.usuario = usuario.getUsuario();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.email = usuario.getEmail();
        this.emailVerificado = usuario.getEmailVerificado();
        this.cpf = usuario.getCpf();
        this.dataNascimento = usuario.getDataNascimento();
        this.senha = usuario.getSenha();
        this.ativo = usuario.getAtivo();
        this.celular = usuario.getCelular();
        this.celularVerificado = usuario.getCelularVerificado();
    }
}