package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import br.com.softsy.educacional.model.AvisoDestinatario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoDestinatarioDTO {
	
	private Long idAvisoDestinatario;
	private LocalDateTime dataCadastro;
	private Long aviso;
	private Long aluno;
	private LocalDateTime dataLeitura;
	
	public AvisoDestinatarioDTO(AvisoDestinatario avisoDestinatario) {
		this.idAvisoDestinatario = avisoDestinatario.getIdAvisoDestinatario();
		this.dataCadastro = avisoDestinatario.getDataCadastro();
		this.aviso = avisoDestinatario.getAviso().getIdAviso();
		this.aluno = avisoDestinatario.getAluno().getIdAluno();
		this.dataLeitura = avisoDestinatario.getDataLeitura();
	}

}
