package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.softsy.educacional.model.AtoRegulatorio;

@Data
@NoArgsConstructor
public class AtoRegulatorioDTO {

	private Long idAtoRegulatorio;
	
	@NotNull
	private Long contaId;
	
	@NotNull
	private String AtoRegulatorio;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public AtoRegulatorioDTO(AtoRegulatorio atoRegulatorio) {
		this.idAtoRegulatorio = atoRegulatorio.getIdAtoRegulatorio();
		this.AtoRegulatorio = atoRegulatorio.getAtoRegulatorio();
		this.dataCadastro = atoRegulatorio.getDataCadastro();
		this.ativo = atoRegulatorio.getAtivo();
		this.contaId = atoRegulatorio.getConta().getIdConta();
	}
	
}
