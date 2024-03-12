package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Periodicidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeriodicidadeDTO {

	private Long idPeriodicidade;
	
	@NotNull
	private String periodicidade;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public PeriodicidadeDTO(Periodicidade periodicidade) {
		this.idPeriodicidade = periodicidade.getIdPeriodicidade();
		this.periodicidade = periodicidade.getPeriodicidade();
		this.dataCadastro = periodicidade.getDataCadastro();
		this.ativo = periodicidade.getAtivo();
	}
}
