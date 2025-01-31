package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.softsy.educacional.model.CursoImg;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoImgDTO {

    private Long idCursoImg;
	 
    private Long contaId; 
    
    private Long cursoId; 
    
    private Long usuarioId; 

    private LocalDateTime dataCadastro; 

    @NotNull
    private String  tipoDispositivo; // Tipo do dispositivo ('D' ou 'M')
    
    @NotNull
    private String pathImg;
    
    @NotNull
    private Integer ordem; 
    
    private String url; 

    public CursoImgDTO(CursoImg cursoImg) {
        this.idCursoImg = cursoImg.getIdCursoImg();
        this.contaId = cursoImg.getConta().getIdConta();
        this.cursoId = cursoImg.getCurso().getIdCurso();
        this.usuarioId = cursoImg.getUsuario().getIdUsuario();
        this.dataCadastro = cursoImg.getDataCadastro();
        this.tipoDispositivo = cursoImg.getTipoDispositivo();
        this.pathImg = cursoImg.getPathImg();
        this.ordem = cursoImg.getOrdem();
        this.url = cursoImg.getUrl();
    }
}
