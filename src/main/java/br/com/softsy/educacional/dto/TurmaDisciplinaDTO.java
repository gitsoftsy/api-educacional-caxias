package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.softsy.educacional.model.TurmaDisciplina;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaDisciplinaDTO {

    private Long idTurmaDisciplina;
    private TurmaDTO turma;
    private DisciplinaDTO disciplina;
    private PeriodoLetivoDTO periodoLetivo;
    private Long serie;
    private CursoDTO curso;
    private CurriculoDTO curriculo;
    private Integer qtdAulasPrevistas;
    private LocalDate dtInicio;
    private LocalDate dtFim;
    private String situacao;
    private String nivelPresenca;
    private LocalTime duracaoAula;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public TurmaDisciplinaDTO(TurmaDisciplina turmaDisciplina) {
        this.idTurmaDisciplina = turmaDisciplina.getIdTurmaDisciplina();
        this.turma = new TurmaDTO(turmaDisciplina.getTurma());
        this.disciplina = new DisciplinaDTO(turmaDisciplina.getDisciplina());
        this.periodoLetivo = new PeriodoLetivoDTO(turmaDisciplina.getPeriodoLetivo());
        this.serie = turmaDisciplina.getSerie();
        this.curso = new CursoDTO(turmaDisciplina.getCurso());
        this.curriculo = new CurriculoDTO(turmaDisciplina.getCurriculo());
        this.qtdAulasPrevistas = turmaDisciplina.getQtdAulasPrevistas();
        this.dtInicio = turmaDisciplina.getDtInicio();
        this.dtFim = turmaDisciplina.getDtFim();
        this.situacao = turmaDisciplina.getSituacao();
        this.nivelPresenca = turmaDisciplina.getNivelPresenca();
        this.duracaoAula = turmaDisciplina.getDuracaoAula();
        this.dataCadastro = turmaDisciplina.getDataCadastro();
        this.ativo = turmaDisciplina.getAtivo();
    }
}