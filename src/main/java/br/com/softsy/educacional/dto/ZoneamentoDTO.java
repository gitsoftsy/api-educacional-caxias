package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Zoneamento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ZoneamentoDTO {
	
	private Long idZoneamento;
	
	@NotNull
	private Long contaId;
	
	@NotNull
	private String zoneamento;
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public ZoneamentoDTO(Zoneamento zoneamento) {
		this.zoneamento = zoneamento.getZoneamento();
		this.idZoneamento = zoneamento.getIdZoneamento();
		this.dataCadastro = zoneamento.getDataCadastro();
		this.ativo = zoneamento.getAtivo();
		this.contaId = zoneamento.getConta().getIdConta();
	}
}