package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Turma;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroTurmaDTO {

    private Long idTurma;
    @NotNull
    private Long escolaId;
    private Long periodoLetivoId;
    @NotNull
    private Long turnoId;
    @NotNull
    private String nomeTurma;
    private String codTurmaInep;
    private Long gradeCurricularId;
    @NotNull
    private Character libras;
    private Character ativo;
    private LocalDateTime dataCadastro;
    @NotNull
    private Integer vagas;
    @NotNull
    private Character controlaVagas;

    public CadastroTurmaDTO(Turma turma) {
        this.idTurma = turma.getIdTurma();
        this.escolaId = turma.getEscola().getIdEscola();
        this.periodoLetivoId = turma.getPeriodoLetivo().getIdPeriodoLetivo();
        this.turnoId = turma.getTurno().getIdTurno();
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
