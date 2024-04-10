package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.LinguaEnsino;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinguaEnsinoDTO {
	
	private Long idLinguaEnsino;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private String linguaEnsino;
	private String linguaIndigena;
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public LinguaEnsinoDTO(LinguaEnsino linguaEnsino) {
		this.linguaEnsino = linguaEnsino.getLinguaEnsino();
		this.linguaIndigena = linguaEnsino.getLinguaIndigena();
		this.idLinguaEnsino = linguaEnsino.getIdLinguaEnsino();
		this.dataCadastro = linguaEnsino.getDataCadastro();
		this.ativo = linguaEnsino.getAtivo();
		this.dependenciaAdmId = linguaEnsino.getDependenciaAdm().getIdDependenciaAdministrativa();
	}

}
