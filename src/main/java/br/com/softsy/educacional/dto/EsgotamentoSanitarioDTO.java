package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EsgotamentoSanitario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EsgotamentoSanitarioDTO {
	
	private Long idEsgotamentoSanitario;
	
	@NotNull
	private String esgotamentoSanitario;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public EsgotamentoSanitarioDTO (EsgotamentoSanitario esgotamentoSanitario) {
		
		this.idEsgotamentoSanitario = esgotamentoSanitario.getIdEsgotamentoSanitario();
		this.esgotamentoSanitario = esgotamentoSanitario.getEsgotamentoSanitario();
		this.dataCadastro = esgotamentoSanitario.getDataCadastro();
		this.ativo = esgotamentoSanitario.getAtivo();
		
	}
}
