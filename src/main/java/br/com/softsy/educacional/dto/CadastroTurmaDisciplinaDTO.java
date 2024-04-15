package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.softsy.educacional.model.TurmaDisciplina;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroTurmaDisciplinaDTO {

    private Long idTurmaDisciplina;
    private Long turmaId;
    private Long disciplinaId;
    private Long periodoLetivoId;
    private Long serie;
    private Long cursoId;
    private Long curriculoId;
    private Integer qtdAulasPrevistas;
    private LocalDate dtInicio;
    private LocalDate dtFim;
    private String situacao;
    private String nivelPresenca;
    private LocalTime duracaoAula;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public CadastroTurmaDisciplinaDTO(TurmaDisciplina turmaDisciplina) {
        this.idTurmaDisciplina = turmaDisciplina.getIdTurmaDisciplina();
        this.turmaId = turmaDisciplina.getTurma().getIdTurma();
        this.disciplinaId = turmaDisciplina.getDisciplina().getIdDisciplina();
        this.periodoLetivoId = turmaDisciplina.getPeriodoLetivo().getIdPeriodoLetivo();
        this.serie = turmaDisciplina.getSerie();
        this.cursoId = turmaDisciplina.getCurso().getIdCurso();
        this.curriculoId = turmaDisciplina.getCurriculo().getIdCurriculo();
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