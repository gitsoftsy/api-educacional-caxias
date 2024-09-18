package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Aviso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoDTO {

    private Long idAviso;
    @NotNull
    private Long alunoId;
    @NotNull
    private Long tipoAvisoId;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    @NotNull
    private String titulo;
    @NotNull
    private String mensagem;
    private Long usuarioId;
    private Long professorId;
    private LocalDateTime dataLeitura;
    private String pathAnexo;
    private Character respostasAbertas;

    public CadastroAvisoDTO(Aviso aviso) {
        this.idAviso = aviso.getIdAviso();
        this.alunoId = aviso.getAluno().getIdAluno();
        this.tipoAvisoId = aviso.getTipoAviso().getIdTipoAviso();
        this.dataCadastro = aviso.getDataCadastro();
        this.dataInicio = aviso.getDataInicio();
        this.dataFim = aviso.getDataFim();
        this.titulo = aviso.getTitulo();
        this.mensagem = aviso.getMensagem();
        this.usuarioId = aviso.getUsuario().getIdUsuario();
        this.professorId = aviso.getProfessor().getIdProfessor();
        this.dataLeitura = aviso.getDataLeitura();
        this.pathAnexo = aviso.getPathAnexo();
        this.respostasAbertas = aviso.getRespostasAbertas();
    }
}