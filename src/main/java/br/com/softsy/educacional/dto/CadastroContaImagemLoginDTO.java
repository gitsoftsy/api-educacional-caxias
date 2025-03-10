package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ContaImagemLogin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroContaImagemLoginDTO {
	
	private Long idContaImagemLogin;

	private Long idConta;

	private LocalDateTime dataCadastro;

	@NotNull
	private String pathImagem;

	private Long aplicacaoId; 
	private String aplicacao; 

	private LocalDateTime dataInicioExibicao;

	private LocalDateTime dataFimExibicao;

	public CadastroContaImagemLoginDTO(ContaImagemLogin contaImagemLogin) {
		this.idContaImagemLogin = contaImagemLogin.getIdContaImagemLogin();
		this.idConta = contaImagemLogin.getConta().getIdConta();
		this.dataCadastro = contaImagemLogin.getDataCadastro();
		this.pathImagem = contaImagemLogin.getPathImagem();
		this.aplicacaoId = contaImagemLogin.getAplicacao().getIdAplicacao(); 
        this.aplicacao = contaImagemLogin.getAplicacao().getAplicacao(); 
		this.dataInicioExibicao = contaImagemLogin.getDataInicioExibicao();
		this.dataFimExibicao = contaImagemLogin.getDataFimExibicao();

	}

}
