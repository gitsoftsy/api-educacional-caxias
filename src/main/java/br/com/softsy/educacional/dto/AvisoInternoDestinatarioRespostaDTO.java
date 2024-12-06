package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.AvisoInternoDestinatarioResposta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoInternoDestinatarioRespostaDTO {
	
	 	private Long idAvisoInternoDestinatarioResposta;
	    private LocalDateTime dataCadastro;
	    private Long avisoInternoDestinatario;
	    private String mensagem;
	    private String pathAnexo;
	    private LocalDateTime dataLeitura;
	    
	    public AvisoInternoDestinatarioRespostaDTO(AvisoInternoDestinatarioResposta avisoInternoDestinatarioResposta) {
	    	
	    	this.idAvisoInternoDestinatarioResposta = avisoInternoDestinatarioResposta.getIdAvisoInternoDestinatarioResposta();
	    	this.dataCadastro = avisoInternoDestinatarioResposta.getDataCadastro();
	    	this.avisoInternoDestinatario = avisoInternoDestinatarioResposta.getAvisoInternoDestinatario().getIdAvisoInternoDestinatario();
	    	this.mensagem = avisoInternoDestinatarioResposta.getMensagem();
	    	this.pathAnexo = avisoInternoDestinatarioResposta.getPathAnexo();
	    	this.dataLeitura = avisoInternoDestinatarioResposta.getDataLeitura();
	    	
	    }

}
