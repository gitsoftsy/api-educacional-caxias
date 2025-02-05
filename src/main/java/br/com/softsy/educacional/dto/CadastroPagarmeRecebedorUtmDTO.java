package br.com.softsy.educacional.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.PagarmeRecebedorUtm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroPagarmeRecebedorUtmDTO {

	private Long idPagarmeRecebedorUtm;

	@NotNull
	private Long contaId;

	@NotNull
//	private Long PagarmeId;

	@NotNull
	private Long utmId;

	private LocalDateTime dataCadastro;

	@NotNull
	private Character ativo;

	@NotNull
	private Character tipoRepasse;

	@NotNull
	private BigDecimal valorRepasse;

	public CadastroPagarmeRecebedorUtmDTO(PagarmeRecebedorUtm pagarmeRecebedorUtm) {

		this.idPagarmeRecebedorUtm = pagarmeRecebedorUtm.getIdPagarmeRecebedorUtm();
		this.contaId = pagarmeRecebedorUtm.getConta().getIdConta();
		// this.PagarmeId = pagarmeRecebedorUtm.getPagarme.getIdPagarme();
		this.utmId = pagarmeRecebedorUtm.getUtm().getIdUtm();
		this.dataCadastro = pagarmeRecebedorUtm.getDataCadastro();
		this.ativo = pagarmeRecebedorUtm.getAtivo();
		this.tipoRepasse = pagarmeRecebedorUtm.getTipoRepasse();
		this.valorRepasse = pagarmeRecebedorUtm.getValorRepasse();

	}

}
