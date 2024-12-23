package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Matricula;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatriculaDTO {

    private Long idMatricula;
    private Long idConta;
    private Character ativo;
    private LocalDateTime dataCadastro;
    private Long idTipoMatricula;
    private Long idAluno;
    private Long idPeriodoLetivo;
    private TurmaDTO turma;
    private String mensagemErro;
    private Character manual;
    private Long idUsuario;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataConfirmacao;
    private String observacao;

    public MatriculaDTO(Matricula matricula) {
        this.idMatricula = matricula.getIdMatricula();
        this.idConta = matricula.getConta().getIdConta();
        this.ativo = matricula.getAtivo();
        this.dataCadastro = matricula.getDataCadastro();
        this.idTipoMatricula = matricula.getTipoMatricula().getIdTipoMatricula();
        this.idAluno = matricula.getAluno().getIdAluno();
        this.idPeriodoLetivo = matricula.getPeriodoLetivo().getIdPeriodoLetivo();
        this.turma = new TurmaDTO(matricula.getTurma());
        this.mensagemErro = matricula.getMensagemErro();
        this.manual = matricula.getManual();
        this.idUsuario = matricula.getUsuario().getIdUsuario();
        this.dataAtualizacao = matricula.getDataAtualizacao();
        this.dataConfirmacao = matricula.getDataConfirmacao();
        this.observacao = matricula.getObservacao();
    }
}
