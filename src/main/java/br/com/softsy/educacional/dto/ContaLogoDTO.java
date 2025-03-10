package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ContaLogo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContaLogoDTO {

	private Long idContaLogo;

	private Long idConta;

	private LocalDateTime dataCadastro;

	private String pathLogo;

	private AplicacaoDTO aplicacao;

	public ContaLogoDTO(ContaLogo contaLogo) {

		this.idContaLogo = contaLogo.getIdContaLogo();
		this.idConta = contaLogo.getConta().getIdConta();
		this.dataCadastro = contaLogo.getDataCadastro();
		this.pathLogo = contaLogo.getPathLogo();
		this.aplicacao = new AplicacaoDTO(contaLogo.getAplicacao());

	}
}
