package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.OfertaConcurso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfertaConcursoDTO {
    private Long idOfertaConcurso;
    private ConcursoDTO concurso;
    private CursoDTO curso;
    private EscolaDTO escola;
    private TurnoDTO turno;
    private SerieDTO serie;
    private CurriculoDTO curriculo;
    private Integer series;
    private String descricaoOferta;
    private Integer vagas;
    private Integer minVagasAbertTurma;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public OfertaConcursoDTO(OfertaConcurso ofertaConcurso) {
        this.idOfertaConcurso = ofertaConcurso.getIdOfertaConcurso();
        this.concurso = new ConcursoDTO(ofertaConcurso.getConcurso());
        this.curso = new CursoDTO(ofertaConcurso.getCurso());
        this.escola = new EscolaDTO(ofertaConcurso.getEscola());
        this.turno = new TurnoDTO(ofertaConcurso.getTurno());
        this.serie = new SerieDTO(ofertaConcurso.getSerie());
        
		if (ofertaConcurso.getCurriculo() != null) {
			this.curriculo = new CurriculoDTO(ofertaConcurso.getCurriculo());
		} else {
			this.curriculo = null;
		}
        this.series = ofertaConcurso.getSeries();
        this.descricaoOferta = ofertaConcurso.getDescricaoOferta();
        this.vagas = ofertaConcurso.getVagas();
        this.minVagasAbertTurma = ofertaConcurso.getMinVagasAbertTurma();
        this.dataCadastro = ofertaConcurso.getDataCadastro();
        this.ativo = ofertaConcurso.getAtivo();
    }
}