package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AvisoInternoDestinatarioResposta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoInternoDestinatarioRespostaDTO {

	private Long idAvisoInternoDestinatarioResposta;
    private LocalDateTime dataCadastro;
    @NotNull
    private Long avisoInternoDestinatario;
    @NotNull
    private String mensagem;
    private String pathAnexo;
    private LocalDateTime dataLeitura;
    
    public CadastroAvisoInternoDestinatarioRespostaDTO(AvisoInternoDestinatarioResposta avisoInternoDestinatarioResposta) {
    	
    	this.idAvisoInternoDestinatarioResposta = avisoInternoDestinatarioResposta.getIdAvisoInternoDestinatarioResposta();
    	this.dataCadastro = avisoInternoDestinatarioResposta.getDataCadastro();
    	this.avisoInternoDestinatario = avisoInternoDestinatarioResposta.getAvisoInternoDestinatario().getIdAvisoInternoDestinatario();
    	this.mensagem = avisoInternoDestinatarioResposta.getMensagem();
    	this.pathAnexo = avisoInternoDestinatarioResposta.getPathAnexo();
    	this.dataLeitura = avisoInternoDestinatarioResposta.getDataLeitura();
    	
    }	
	
}
