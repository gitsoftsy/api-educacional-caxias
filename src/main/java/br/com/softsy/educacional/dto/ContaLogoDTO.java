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

	private Long contaId;

	private LocalDateTime dataCadastro;

	private String pathLogo;

	private Long aplicacaoId;

	public ContaLogoDTO(ContaLogo contaLogo) {

		this.idContaLogo = contaLogo.getIdContaLogo();
		this.contaId = contaLogo.getConta().getIdConta();
		this.dataCadastro = contaLogo.getDataCadastro();
		this.pathLogo = contaLogo.getPathLogo();
		this.aplicacaoId = contaLogo.getAplicacao().getIdAplicacao();

	}
}
