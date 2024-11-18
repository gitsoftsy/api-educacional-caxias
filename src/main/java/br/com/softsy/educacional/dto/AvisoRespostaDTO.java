package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;
import br.com.softsy.educacional.model.AvisoResposta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoRespostaDTO {

    private Long idAvisoResposta;
    private LocalDateTime dataCadastro;
    private Long aviso;
    private Long aluno;
    private Long usuario;
    private Long professor;
    private String mensagem;
    private String pathAnexo;

    public AvisoRespostaDTO(AvisoResposta avisoResposta) {
        this.idAvisoResposta = avisoResposta.getIdAvisoResposta();
        this.dataCadastro = avisoResposta.getDataCadastro();
        this.aviso = avisoResposta.getAviso().getIdAviso(); 
        this.aluno = avisoResposta.getAluno().getIdAluno();
        this.usuario = avisoResposta.getUsuario().getIdUsuario();
        this.professor = avisoResposta.getProfessor().getIdProfessor();
        this.mensagem = avisoResposta.getMensagem();
        this.pathAnexo = avisoResposta.getPathAnexo();
    }
}
