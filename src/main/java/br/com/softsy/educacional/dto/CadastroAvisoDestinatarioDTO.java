package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AvisoDestinatario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoDestinatarioDTO {
	
	private Long idAvisoDestinatario;
	private LocalDateTime dataCadastro;
	@NotNull
	private Long avisoId;
	@NotNull
	private Long alunoId;
	private LocalDateTime dataLeitura;
	
	public CadastroAvisoDestinatarioDTO(AvisoDestinatario avisoDestinatario) {
		this.idAvisoDestinatario = avisoDestinatario.getIdAvisoDestinatario();
		this.dataCadastro = avisoDestinatario.getDataCadastro();
		this.avisoId = avisoDestinatario.getAviso().getIdAviso();
		this.alunoId = avisoDestinatario.getAluno().getIdAluno();
		this.dataLeitura = avisoDestinatario.getDataLeitura();
	}

}
