package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_PROVEDOR_INTERNET")
@Data
public class ProvedorInternet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROVEDOR_INTERNET")
	private Long idProvedorInternet;
	
	@Column(name = "PROVEDOR_INTERNET", nullable = false, unique = true)
	private String provedorInternet;
	
	@Column(name = "TELEFONE_PROVEDOR")
	private String telefoneProvedor;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
