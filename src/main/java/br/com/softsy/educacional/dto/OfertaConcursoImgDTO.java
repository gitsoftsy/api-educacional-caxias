package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.OfertaConcursoImg;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfertaConcursoImgDTO {
	
    private Long idOfertaConcursoImg;
	 
    private Long contaId; 
    
    private Long ofertaConcursoId; 
    
    private Long usuarioId; 

    private LocalDateTime dataCadastro; 

    @NotNull
    private String  tipoDispositivo; // Tipo do dispositivo ('D' ou 'M')
    
    @NotNull
    private String pathImg;
    
    @NotNull
    private Integer ordem; 
    
    private String url; 

    public OfertaConcursoImgDTO(OfertaConcursoImg ofertaConcursoImg) {
        this.idOfertaConcursoImg = ofertaConcursoImg.getIdOfertaConcursoImg();
        this.contaId = ofertaConcursoImg.getConta().getIdConta();
        this.ofertaConcursoId = ofertaConcursoImg.getOfertaConcurso().getIdOfertaConcurso();
        this.usuarioId = ofertaConcursoImg.getUsuario().getIdUsuario();
        this.dataCadastro = ofertaConcursoImg.getDataCadastro();
        this.tipoDispositivo = ofertaConcursoImg.getTipoDispositivo();
        this.pathImg = ofertaConcursoImg.getPathImg();
        this.ordem = ofertaConcursoImg.getOrdem();
        this.url = ofertaConcursoImg.getUrl();
    }	
}
