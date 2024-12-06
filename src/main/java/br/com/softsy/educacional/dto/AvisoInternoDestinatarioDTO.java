package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.AvisoInternoDestinatario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoInternoDestinatarioDTO {
	
	private Long idAvisoInternoDestinatario;
	private LocalDateTime dataCadastro;
	private Long avisoInterno;
	private Long usuarioDestinatario;
	private Long professorDestinatario;
	private LocalDateTime dataLeitura;
	
	public AvisoInternoDestinatarioDTO (AvisoInternoDestinatario avisoInternoDestinatario) {
		
		this.idAvisoInternoDestinatario = avisoInternoDestinatario.getIdAvisoInternoDestinatario();
		this.dataCadastro = avisoInternoDestinatario.getDataCadastro();
		this.avisoInterno = avisoInternoDestinatario.getAvisoInterno().getIdAvisoInterno();
		this.usuarioDestinatario = avisoInternoDestinatario.getUsuarioDestinatario() != null ? avisoInternoDestinatario.getUsuarioDestinatario().getIdUsuario() : null;
		this.professorDestinatario = avisoInternoDestinatario.getProfessorDestinatario() != null ? avisoInternoDestinatario.getProfessorDestinatario().getIdProfessor() : null;
		this.dataLeitura = avisoInternoDestinatario.getDataLeitura();
		
		
	}

}
