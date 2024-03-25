package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Turma;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaDTO {

    private Long idTurma;
    private Long escolaId;
    @NotNull
    private Integer anoVigente;
    @NotNull
    private AnoEscolarDTO anoEscolar;
    @NotNull
    private String numTurma;
    @NotNull
    private String codTurmaInep;
    @NotNull
    private FormaOrganEnsinoDTO formaOrganEnsino;
    @NotNull
    private TipoDeMedicaoDTO tipoDeMedicao;
    @NotNull
    private TurnoDTO turno;
    @NotNull
    private TipoAtendimentoDTO tipoAtendimento;
    @NotNull
    private ModalidadeEscolaDTO modalidadeEscola;
    @NotNull
    private Character libras;
    @NotNull
    private Integer vagas;

    public TurmaDTO(Turma turma) {
    	
        this.idTurma = turma.getIdTurma();
        this.escolaId = turma.getEscola().getIdEscola();     
        this.anoVigente = turma.getAnoVigente();
        this.anoEscolar = new AnoEscolarDTO(turma.getAnoEscolar());
        this.numTurma = turma.getNumTurma();
        this.codTurmaInep = turma.getCodTurmaInep();
        this.formaOrganEnsino = new FormaOrganEnsinoDTO(turma.getFormaOrganEnsino());
        this.tipoDeMedicao = new TipoDeMedicaoDTO(turma.getTipoDeMedicao());
        this.turno = new TurnoDTO(turma.getTurno());
        this.tipoAtendimento = new TipoAtendimentoDTO(turma.getTipoAtendimento());
        this.modalidadeEscola = new ModalidadeEscolaDTO(turma.getModalidadeEscola());
        this.libras = turma.getLibras();
        this.vagas = turma.getVagas();
    }
}
