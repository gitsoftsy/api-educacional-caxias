package br.com.softsy.educacional.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

import lombok.Data;

@Entity
@Table(name = "TBL_CONTA_IMAGEM_LOGIN")
@Data
public class ContaImagemLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CONTA_IMAGEM_LOGIN")
	private Long idContaImagemLogin;

	@ManyToOne
	@JoinColumn(name = "ID_CONTA", nullable = false)
	private Conta conta;

	@Column(name = "DT_CADASTRO")
	private LocalDateTime dataCadastro;

	@Column(name = "PATH_IMAGEM")
	private String pathImagem;

	@ManyToOne
	@JoinColumn(name = "ID_APLICACAO", nullable = false)
	private Aplicacao aplicacao;

	@Column(name = "DT_INI_EXIB")
	private LocalDateTime dataInicioExibicao;

	@Column(name = "DT_FIM_EXIB")
	private LocalDateTime dataFimExibicao;

}