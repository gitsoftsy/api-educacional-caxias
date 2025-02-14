package br.com.softsy.educacional.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.PagarmeRecebedorCurso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroPagarmeRecebedorCursoDTO {
	
	private Long idPagarmeRecebedorCurso;

	@NotNull
	private Long contaId;

	@NotNull
	private Long cursoId;

	private LocalDateTime dataCadastro;

	@NotNull
	private Character ativo;

	@NotNull
	private Character tipoRepasse;

	@NotNull
	private BigDecimal valorRepasse;

	public CadastroPagarmeRecebedorCursoDTO(PagarmeRecebedorCurso pagarmeRecebedorCurso) {

		this.idPagarmeRecebedorCurso = pagarmeRecebedorCurso.getIdPagarmeRecebedorCurso();
		this.contaId = pagarmeRecebedorCurso.getConta().getIdConta();
		// this.PagarmeId = pagarmeRecebedorUtm.getPagarme.getIdPagarme();
		this.cursoId = pagarmeRecebedorCurso.getCurso().getIdCurso();
		this.dataCadastro = pagarmeRecebedorCurso.getDataCadastro();
		this.ativo = pagarmeRecebedorCurso.getAtivo();
		this.tipoRepasse = pagarmeRecebedorCurso.getTipoRepasse();
		this.valorRepasse = pagarmeRecebedorCurso.getValorRepasse();

	}

}
