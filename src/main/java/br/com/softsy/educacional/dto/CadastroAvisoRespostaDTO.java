package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AvisoResposta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoRespostaDTO {

	 	private Long idAvisoResposta;
	    private LocalDateTime dataCadastro;
	    @NotNull
	    private Long avisoId;
	    @NotNull
	    private Long alunoId;
	    private Long usuarioId;
	    private Long professorId;
	    @NotNull
	    private String mensagem;
	    private String pathAnexo;

	    public CadastroAvisoRespostaDTO(AvisoResposta avisoResposta) {
	        this.idAvisoResposta = avisoResposta.getIdAvisoResposta();
	        this.dataCadastro = avisoResposta.getDataCadastro();
	        this.avisoId = avisoResposta.getAviso().getIdAviso(); 
	        this.alunoId = avisoResposta.getAluno().getIdAluno();
	        this.usuarioId = avisoResposta.getUsuario().getIdUsuario();
	        this.professorId = avisoResposta.getProfessor().getIdProfessor();
	        this.mensagem = avisoResposta.getMensagem();
	        this.pathAnexo = avisoResposta.getPathAnexo();
	    }
	
}
