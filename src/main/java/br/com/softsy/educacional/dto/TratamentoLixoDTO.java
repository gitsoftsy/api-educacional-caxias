package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TratamentoLixo;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TratamentoLixoDTO {
	
	private Long idTratamentoLixo;
	
	@NotNull
	private Long contaId;
	
	@NotNull
	private String tratamentoLixo;
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public TratamentoLixoDTO(TratamentoLixo tratamentoLixo) {
		this.tratamentoLixo= tratamentoLixo.getTratamentoLixo();
		this.idTratamentoLixo = tratamentoLixo.getIdTratamentoLixo();
		this.dataCadastro = tratamentoLixo.getDataCadastro();
		this.ativo = tratamentoLixo.getAtivo();
		this.contaId = tratamentoLixo.getConta().getIdConta();
	}


}
