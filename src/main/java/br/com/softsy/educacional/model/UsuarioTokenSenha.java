package br.com.softsy.educacional.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "TBL_USUARIO_TOKEN_SENHA")
@Data
public class UsuarioTokenSenha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO_TOKEN_SENHA")
	private Long idUsuarioTokenSenha;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;

    @Column(name = "ATIVO", nullable = false)
    private Character ativo;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private Usuario usuario;
	
	@Column(name = "TOKEN", length = 10)
    private String token;
	
	@Column(name = "DT_VALIDADE", columnDefinition = "DATETIME NOT NULL")
	private LocalDateTime dataValidade;

	@Column(name = "UTILIZADO", nullable = false)
    private Character utilizado;
}
