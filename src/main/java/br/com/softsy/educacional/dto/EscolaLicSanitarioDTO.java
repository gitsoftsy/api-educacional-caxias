package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaLicSanitario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaLicSanitarioDTO {
	
	private Long idEscolaLicSanitario;
	private Long escolaId;
	@NotNull
	private String licSanitario;
	@NotNull
	private LocalDateTime dataEmissao;
	@NotNull
	private LocalDateTime dataValidade;
	private String anexo;
	private LocalDateTime dataCadastro;
	
	public EscolaLicSanitarioDTO(EscolaLicSanitario licSanitario) {
		this.idEscolaLicSanitario = licSanitario.getIdEscolaLicSanitario();
		this.escolaId = licSanitario.getEscola().getIdEscola();
		this.licSanitario = licSanitario.getLicSanitario();
		this.dataEmissao = licSanitario.getDataEmissao();
		this.dataCadastro = licSanitario.getDataCadastro();
		this.dataValidade = licSanitario.getDataValidade();
		this.anexo = licSanitario.getAnexo();
	}

}
