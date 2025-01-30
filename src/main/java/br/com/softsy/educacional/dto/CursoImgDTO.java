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
    
    private ContaDTO idConta; 
    
    private CursoDTO idCurso; 

    private UsuarioDTO idUsuario; 

    private LocalDateTime dataCadastro; 

    private String  tipoDispositivo; // Tipo do dispositivo ('D' ou 'M')
    
    private String pathImg;
    
    private Integer ordem; 
    
    private String url; 

    public CursoImgDTO(CursoImg cursoImg) {
        this.idCursoImg = cursoImg.getIdCursoImg();
        this.idConta = new ContaDTO(cursoImg.getConta());
        this.idCurso = new CursoDTO(cursoImg.getCurso());
        this.idUsuario = new UsuarioDTO(cursoImg.getUsuario());
        this.dataCadastro = cursoImg.getDataCadastro();
        this.tipoDispositivo = cursoImg.getTipoDispositivo();
        this.pathImg = cursoImg.getPathImg();
        this.ordem = cursoImg.getOrdem();
        this.url = cursoImg.getUrl();
    }
}
