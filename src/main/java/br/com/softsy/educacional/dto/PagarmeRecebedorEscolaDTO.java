package br.com.softsy.educacional.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.PagarmeRecebedorEscola;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagarmeRecebedorEscolaDTO {
	

	private Long idPagarmeRecebedorEscola;

	private Long contaId;

//	private Long PagarmeId;

	private Long escolaId;

	private LocalDateTime dataCadastro;

	private Character ativo;

	private Character tipoRepasse;

	private BigDecimal valorRepasse;

	public PagarmeRecebedorEscolaDTO(PagarmeRecebedorEscola pagarmeRecebedorEscola) {

		this.idPagarmeRecebedorEscola = pagarmeRecebedorEscola.getIdPagarmeRecebedorEscola();
		this.contaId = pagarmeRecebedorEscola.getConta().getIdConta();
		// this.PagarmeId = pagarmeRecebedorUtm.getPagarme.getIdPagarme();
		this.escolaId = pagarmeRecebedorEscola.getEscola().getIdEscola();
		this.dataCadastro = pagarmeRecebedorEscola.getDataCadastro();
		this.ativo = pagarmeRecebedorEscola.getAtivo();
		this.tipoRepasse = pagarmeRecebedorEscola.getTipoRepasse();
		this.valorRepasse = pagarmeRecebedorEscola.getValorRepasse();

	}

}
