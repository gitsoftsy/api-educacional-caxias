package br.com.softsy.educacional.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.PagarmeRecebedorEscola;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroPagarmeRecebedorEscolaDTO {
	
	private Long idPagarmeRecebedorEscola;

	@NotNull
	private Long contaId;

	@NotNull
//	private Long PagarmeId;

	@NotNull
	private Long EscolaId;

	private LocalDateTime dataCadastro;

	@NotNull
	private Character ativo;

	@NotNull
	private Character tipoRepasse;

	@NotNull
	private BigDecimal valorRepasse;

	public CadastroPagarmeRecebedorEscolaDTO(PagarmeRecebedorEscola pagarmeRecebedorEscola) {

		this.idPagarmeRecebedorEscola = pagarmeRecebedorEscola.getIdPagarmeRecebedorEscola();
		this.contaId = pagarmeRecebedorEscola.getConta().getIdConta();
		// this.PagarmeId = pagarmeRecebedorUtm.getPagarme.getIdPagarme();
		this.EscolaId = pagarmeRecebedorEscola.getEscola().getIdEscola();
		this.dataCadastro = pagarmeRecebedorEscola.getDataCadastro();
		this.ativo = pagarmeRecebedorEscola.getAtivo();
		this.tipoRepasse = pagarmeRecebedorEscola.getTipoRepasse();
		this.valorRepasse = pagarmeRecebedorEscola.getValorRepasse();

	}

}
