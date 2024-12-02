package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.AvisoDestinatarioResposta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoDestinatarioRespostaDTO {
	
    private Long idAvisoDestinatarioResposta;
    
    private LocalDateTime dataCadastro;
    private Long avisoDestinatario;
    private String mensagem;
    private String pathAnexo;
    private LocalDateTime dataLeitura;
    
    public AvisoDestinatarioRespostaDTO(AvisoDestinatarioResposta avisoDestinatarioResposta) {
    	this.idAvisoDestinatarioResposta = avisoDestinatarioResposta.getIdAvisoDestinatarioResposta();
    	this.dataCadastro = avisoDestinatarioResposta.getDataCadastro();
    	this.avisoDestinatario = avisoDestinatarioResposta.getAvisoDestinatario().getIdAvisoDestinatario();
    	this.mensagem = avisoDestinatarioResposta.getMensagem();
    	this.pathAnexo = avisoDestinatarioResposta.getPathAnexo();
    	this.dataLeitura = avisoDestinatarioResposta.getDataLeitura();
    }

}
