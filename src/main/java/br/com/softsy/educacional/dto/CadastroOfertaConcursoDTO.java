package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.OfertaConcurso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroOfertaConcursoDTO {
    private Long idOfertaConcurso;
    private Long concursoId;
    private Long cursoId;
    private Long escolaId;
    private Long turnoId;
    private Long serieId;
    private Long curriculoId;
    private String descricaoOferta;
    private Integer vagas;
    private Integer minVagasAbertTurma;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public CadastroOfertaConcursoDTO(OfertaConcurso ofertaConcurso) {
        this.idOfertaConcurso = ofertaConcurso.getIdOfertaConcurso();
        this.concursoId = ofertaConcurso.getConcurso().getIdConcurso();
        this.cursoId = ofertaConcurso.getCurso().getIdCurso();
        this.escolaId = ofertaConcurso.getEscola().getIdEscola();
        this.turnoId = ofertaConcurso.getTurno().getIdTurno();
        this.serieId = ofertaConcurso.getSerie().getIdSerie();
        this.curriculoId = ofertaConcurso.getCurriculo()!= null ? ofertaConcurso.getCurriculo().getIdCurriculo() : null;
        this.descricaoOferta = ofertaConcurso.getDescricaoOferta();
        this.vagas = ofertaConcurso.getVagas();
        this.minVagasAbertTurma = ofertaConcurso.getMinVagasAbertTurma();
        this.dataCadastro = ofertaConcurso.getDataCadastro();
        this.ativo = ofertaConcurso.getAtivo();
    }
}