package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AvisoDestinatarioResposta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoDestinatarioRespostaDTO {

private Long idAvisoDestinatarioResposta;
    
    private LocalDateTime dataCadastro;
    @NotNull
    private Long avisoDestinatarioId;
    @NotNull
    private String mensagem;
    private String pathAnexo;
    private LocalDateTime dataLeitura;
    
    public CadastroAvisoDestinatarioRespostaDTO(AvisoDestinatarioResposta avisoDestinatarioResposta) {
    	this.idAvisoDestinatarioResposta = avisoDestinatarioResposta.getIdAvisoDestinatarioResposta();
    	this.dataCadastro = avisoDestinatarioResposta.getDataCadastro();
    	this.avisoDestinatarioId = avisoDestinatarioResposta.getAvisoDestinatario().getIdAvisoDestinatario();
    	this.mensagem = avisoDestinatarioResposta.getMensagem();
    	this.pathAnexo = avisoDestinatarioResposta.getPathAnexo();
    	this.dataLeitura = avisoDestinatarioResposta.getDataLeitura();
    }
	
}
