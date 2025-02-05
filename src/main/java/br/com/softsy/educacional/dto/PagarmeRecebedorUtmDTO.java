package br.com.softsy.educacional.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.PagarmeRecebedorUtm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagarmeRecebedorUtmDTO {

	private Long idPagarmeRecebedorUtm;

	private Long contaId;

//	private Long PagarmeId;

	private Long utmId;

	private LocalDateTime dataCadastro;

	private Character ativo;

	private Character tipoRepasse;

	private BigDecimal valorRepasse;

	public PagarmeRecebedorUtmDTO(PagarmeRecebedorUtm pagarmeRecebedorUtm) {

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
