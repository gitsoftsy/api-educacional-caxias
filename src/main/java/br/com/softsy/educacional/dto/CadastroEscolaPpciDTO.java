package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;

import br.com.softsy.educacional.model.EscolaPpci;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaPpciDTO {

	private Long idEscolaPpci;

	private LocalDateTime dataCadastro;
	private LocalDateTime dataValidade;
	private LocalDateTime dataEmissao;

	@NotNull
	private String ppci;

	private String anexo;

	@NotNull
	private Long escolaId;

	public CadastroEscolaPpciDTO(EscolaPpci escolaPpci) {
		this.idEscolaPpci = escolaPpci.getIdEscolaPpci();
		this.dataCadastro = escolaPpci.getDataCadastro();
		this.dataValidade = escolaPpci.getDataValidade();
		this.dataEmissao = escolaPpci.getDataEmissao();
		this.ppci = escolaPpci.getPpci();
		this.anexo = Base64.encodeBase64String(escolaPpci.getAnexo());
		this.escolaId = escolaPpci.getEscola().getIdEscola();
	}

}
