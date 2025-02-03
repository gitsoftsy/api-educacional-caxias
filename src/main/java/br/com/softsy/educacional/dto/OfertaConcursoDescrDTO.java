package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.CursoDescr;
import br.com.softsy.educacional.model.OfertaConcursoDescr;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfertaConcursoDescrDTO {

    private Long idOfertaConcursoDescr;
    private Long contaId;
    private OfertaConcursoDTO ofertaConcurso;
    private Long usuarioId;
    private LocalDateTime dataCadastro;
    private String titulo;
    private String descricao;
    private Integer ordem;

    public OfertaConcursoDescrDTO(OfertaConcursoDescr ofertaConcursoDescr) {
        this.idOfertaConcursoDescr = ofertaConcursoDescr.getIdOfertaConcursoDescr();
        this.contaId = ofertaConcursoDescr.getConta().getIdConta();
        this.ofertaConcurso = new OfertaConcursoDTO(ofertaConcursoDescr.getOfertaConcurso());
        this.usuarioId = ofertaConcursoDescr.getUsuario().getIdUsuario();
        this.dataCadastro = ofertaConcursoDescr.getDataCadastro();
        this.titulo = ofertaConcursoDescr.getTitulo();
        this.descricao = ofertaConcursoDescr.getDescricao();
        this.ordem = ofertaConcursoDescr.getOrdem();
    }
	
}
