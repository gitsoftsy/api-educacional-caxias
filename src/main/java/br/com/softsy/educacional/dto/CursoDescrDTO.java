package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.CursoDescr;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoDescrDTO {
    private Long idCursoDescr;
    private Long contaId;
    private CursoDTO curso;
    private Long usuarioId;
    private LocalDateTime dataCadastro;
    private String titulo;
    private String descricao;
    private Integer ordem;

    public CursoDescrDTO(CursoDescr cursoDescr) {
        this.idCursoDescr = cursoDescr.getIdCursoDescr();
        this.contaId = cursoDescr.getConta().getIdConta();
        this.curso = new CursoDTO(cursoDescr.getCurso());
        this.usuarioId = cursoDescr.getUsuario().getIdUsuario();
        this.dataCadastro = cursoDescr.getDataCadastro();
        this.titulo = cursoDescr.getTitulo();
        this.descricao = cursoDescr.getDescricao();
        this.ordem = cursoDescr.getOrdem();
    }
}