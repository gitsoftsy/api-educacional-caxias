package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AvisoInternoResposta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoInternoRespostaDTO {

	private Long idAvisoInternoResposta;
    private LocalDateTime dataCadastro;
    @NotNull
    private Long avisoInternoId;
    private Long usuarioId;
    private Long professorId;
    @NotNull
    private String mensagem;
    private String pathAnexo;
	  
	  public CadastroAvisoInternoRespostaDTO (AvisoInternoResposta avisoInternoResposta) {
		  
		  this.idAvisoInternoResposta = avisoInternoResposta.getIdAvisoInternoResposta();
		  this.dataCadastro = avisoInternoResposta.getDataCadastro();
		  this.avisoInternoId = avisoInternoResposta.getAvisoInterno().getIdAvisoInterno();
		  this.usuarioId = avisoInternoResposta.getUsuario().getIdUsuario();
	      this.professorId = avisoInternoResposta.getProfessor().getIdProfessor();
	      this.mensagem = avisoInternoResposta.getMensagem();
	      this.pathAnexo = avisoInternoResposta.getPathAnexo();
		  
		  
	  }
	  
}
