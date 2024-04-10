package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ModalidadeEscola;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModalidadeEscolaDTO {
	
	private Long idModalidadeEscola;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private String modalidadeEscola;
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public ModalidadeEscolaDTO(ModalidadeEscola modalidadeEscola) {
		this.modalidadeEscola = modalidadeEscola.getModalidadeEscola();
		this.idModalidadeEscola = modalidadeEscola.getIdModalidadeEscola();
		this.dataCadastro = modalidadeEscola.getDataCadastro();
		this.ativo = modalidadeEscola.getAtivo();
		this.dependenciaAdmId = modalidadeEscola.getDependenciaAdm().getIdDependenciaAdministrativa();
	}

}
