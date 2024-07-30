package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Turma;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaDTO {

    private Long idTurma;
    private Long escolaId;
    private PeriodoLetivoDTO periodoLetivo;
    private TurnoDTO turno;
    @NotNull
    private String nomeTurma;
    private String codTurmaInep;
    private Long gradeCurricularId;
    @NotNull
    private Character libras;
    private LocalDateTime dataCadastro;
    @NotNull
    private Character ativo;
    @NotNull
    private Integer vagas;
    @NotNull
    private Character controlaVagas;

    public TurmaDTO(Turma turma) {
        this.idTurma = turma.getIdTurma();
        this.escolaId = turma.getEscola().getIdEscola();
        this.periodoLetivo = new PeriodoLetivoDTO(turma.getPeriodoLetivo());
        this.turno = new TurnoDTO(turma.getTurno());
        this.nomeTurma = turma.getNomeTurma();
        this.codTurmaInep = turma.getCodTurmaInep();
        this.gradeCurricularId = turma.getGradeCurricular().getIdGradeCurricular();
        this.libras = turma.getLibras();
        this.dataCadastro = turma.getDataCadastro();
        this.ativo = turma.getAtivo();
        this.vagas = turma.getVagas();
        this.controlaVagas = turma.getControlaVagas();
    }
}
