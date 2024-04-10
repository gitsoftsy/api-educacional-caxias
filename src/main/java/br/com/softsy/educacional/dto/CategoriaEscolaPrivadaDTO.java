package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.CategoriaEscolaPrivada;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaEscolaPrivadaDTO {
	
	private Long idCategoriaEscolaPrivada;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private String categoriaEscolaPrivada;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public CategoriaEscolaPrivadaDTO(CategoriaEscolaPrivada categoriaEscolaPrivada) {
		this.idCategoriaEscolaPrivada = categoriaEscolaPrivada.getIdCategoriaEscolaPrivada();
		this.categoriaEscolaPrivada = categoriaEscolaPrivada.getCategoriaEscolaPrivada();
		this.dataCadastro = categoriaEscolaPrivada.getDataCadastro();
		this.ativo = categoriaEscolaPrivada.getAtivo();
		this.dependenciaAdmId = categoriaEscolaPrivada.getDependenciaAdm().getIdDependenciaAdministrativa();
	}
}
