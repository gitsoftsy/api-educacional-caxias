package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AvisoInternoDestinatario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoInternoDestinatarioDTO {

	private Long idAvisoInternoDestinatario;
	private LocalDateTime dataCadastro;
	@NotNull
	private Long avisoInternoId;
	@NotNull
	private Long usuarioDestinatarioId;
	private Long professorDestinatarioId;
	@NotNull
	private LocalDateTime dataLeitura;
	
	public CadastroAvisoInternoDestinatarioDTO(AvisoInternoDestinatario avisoInternoDestinatario) {
		
		this.idAvisoInternoDestinatario = avisoInternoDestinatario.getIdAvisoInternoDestinatario();
		this.dataCadastro = avisoInternoDestinatario.getDataCadastro();
		this.avisoInternoId = avisoInternoDestinatario.getAvisoInterno().getIdAvisoInterno();
		this.usuarioDestinatarioId = avisoInternoDestinatario.getUsuarioDestinatario().getIdUsuario();
		this.professorDestinatarioId = avisoInternoDestinatario.getProfessorDestinatario().getIdProfessor();
		this.dataLeitura = avisoInternoDestinatario.getDataLeitura();
	}
}
