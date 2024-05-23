package br.com.softsy.educacional.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_USUARIO")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "USUARIO", nullable = false, length = 50)
    private String usuario;

    @Column(name = "NOME_COMPL", nullable = false, length = 255)
    private String nomeCompleto;

    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @Column(name = "EMAIL_VERIFICADO", length = 1)
    private Character emailVerificado;

    @Column(name = "CPF", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "DT_NASCIMENTO")
    private LocalDateTime dataNascimento;

    @Column(name = "SENHA", nullable = false, length = 255)
    private String senha;

    @Column(name = "ATIVO", nullable = false, length = 1)
    private Character ativo;

    @Column(name = "CELULAR", length = 11)
    private String celular;

    @Column(name = "CELULAR_VERIFICADO", length = 1)
    private Character celularVerificado;
    
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Set<UsuarioConta> usuarioConta = new HashSet<>();
}