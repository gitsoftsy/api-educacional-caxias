package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UsuarioLogadoDTO {
	
    private Long idUsuario;
    private LocalDateTime dataCadastro;
    private String usuario;
    private String nomeCompleto;
    private String email;
    private Character emailVerificado;
    private String cpf;
    private LocalDateTime dataNascimento;
    private String senha;
    private Character ativo;
    private String celular;
    private Character celularVerificado;

    public UsuarioLogadoDTO(UsuarioLogadoDTO usuario) {
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
