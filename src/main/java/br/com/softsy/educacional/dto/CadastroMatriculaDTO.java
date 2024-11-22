package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Matricula;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroMatriculaDTO {

    private Long idMatricula;
    @NotNull
    private Long contaId;
    @NotNull
    private Character ativo;
    private LocalDateTime dataCadastro;
    @NotNull
    private Long tipoMatriculaId;
    @NotNull
    private Long alunoId;
    @NotNull
    private Long periodoLetivoId;
    private Long turmaId;
    private String mensagemErro;
    @NotNull
    private Character manual;
    private Long usuarioId;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataConfirmacao;
    private String observacao;

    public CadastroMatriculaDTO(Matricula matricula) {
        this.idMatricula = matricula.getIdMatricula();
        this.contaId = matricula.getConta().getIdConta();
        this.ativo = matricula.getAtivo();
        this.dataCadastro = matricula.getDataCadastro();
        this.tipoMatriculaId = matricula.getTipoMatricula().getIdTipoMatricula();
        this.alunoId = matricula.getAluno().getIdAluno();
        this.periodoLetivoId = matricula.getPeriodoLetivo().getIdPeriodoLetivo();
        this.turmaId = matricula.getTurma().getIdTurma();
        this.mensagemErro = matricula.getMensagemErro();
        this.manual = matricula.getManual();
        this.usuarioId = matricula.getUsuario().getIdUsuario();
        this.dataAtualizacao = matricula.getDataAtualizacao();
        this.dataConfirmacao = matricula.getDataConfirmacao();
        this.observacao = matricula.getObservacao();
    }



	
}
