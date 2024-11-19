package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.AvisoInternoResposta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoInternoRespostaDTO {

    private Long idAvisoInternoResposta;
    private LocalDateTime dataCadastro;
    private Long avisoInterno;
    private Long usuario;
    private Long professor;
    private String mensagem;
    private String pathAnexo;

    public AvisoInternoRespostaDTO(AvisoInternoResposta avisoInternoResposta) {
        this.idAvisoInternoResposta = avisoInternoResposta.getIdAvisoInternoResposta();
        this.dataCadastro = avisoInternoResposta.getDataCadastro();
        this.avisoInterno = avisoInternoResposta.getAvisoInterno().getIdAvisoInterno();
        this.usuario = avisoInternoResposta.getUsuario().getIdUsuario();
        this.professor = avisoInternoResposta.getProfessor().getIdProfessor();
        this.mensagem = avisoInternoResposta.getMensagem();
        this.pathAnexo = avisoInternoResposta.getPathAnexo();
    }
}
