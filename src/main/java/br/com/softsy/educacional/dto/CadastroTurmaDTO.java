package br.com.softsy.educacional.dto;

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
    @NotNull
    private Integer anoVigente;
    @NotNull
    private Long anoEscolarId;
    @NotNull
    private String numTurma;
    @NotNull
    private String codTurmaInep;
    @NotNull
    private Long formaOrganEnsinoId;
    @NotNull
    private Long tipoDeMedicaoId;
    @NotNull
    private Long turnoId;
    @NotNull
    private Long tipoAtendimentoId;
    @NotNull
    private Long modalidadeEscolaId;
    @NotNull
    private Character libras;
    @NotNull
    private Integer vagas;

    public CadastroTurmaDTO(Turma turma) {
        this.idTurma = turma.getIdTurma();
        this.escolaId = turma.getEscola().getIdEscola();
        this.anoVigente = turma.getAnoVigente();
        this.anoEscolarId = turma.getAnoEscolar().getIdAnoEscolar();
        this.numTurma = turma.getNumTurma();
        this.codTurmaInep = turma.getCodTurmaInep();
        this.formaOrganEnsinoId = turma.getFormaOrganEnsino().getIdFormaOrganEnsino();
        this.tipoDeMedicaoId = turma.getTipoDeMedicao().getIdTipoMedicao();
        this.turnoId = turma.getTurno().getIdTurno();
        this.tipoAtendimentoId = turma.getTipoAtendimento().getIdTipoAtendimento();
        this.modalidadeEscolaId = turma.getModalidadeEscola().getIdModalidadeEscola();
        this.libras = turma.getLibras();
        this.vagas = turma.getVagas();
    }
}
