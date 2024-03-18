package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EntidadeSuperior;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntidadeSuperiorDTO {
	
	private Long idEntidadeSuperior;
	
	@NotNull
	private String entidadeSuperior;
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public EntidadeSuperiorDTO(EntidadeSuperior entidadeSuperior) {
		this.entidadeSuperior = entidadeSuperior.getEntidadeSuperior();
		this.idEntidadeSuperior = entidadeSuperior.getIdEntidadeSuperior();
		this.dataCadastro = entidadeSuperior.getDataCadastro();
		this.ativo = entidadeSuperior.getAtivo();
	}
}
