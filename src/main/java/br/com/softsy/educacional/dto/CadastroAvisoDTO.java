package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Aviso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoDTO {

    private Long idAviso;
    private Long contaId;
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
    private String pathAnexo;
    private Character permiteResposta;
    private List<Long> destinatario;

    public CadastroAvisoDTO(Aviso aviso) {
        this.idAviso = aviso.getIdAviso();
        this.contaId = aviso.getConta().getIdConta();
        this.tipoAvisoId = aviso.getTipoAviso().getIdTipoAviso();
        this.dataCadastro = aviso.getDataCadastro();
        this.dataInicio = aviso.getDataInicio();
        this.dataFim = aviso.getDataFim();
        this.titulo = aviso.getTitulo();
        this.mensagem = aviso.getMensagem();
        if(aviso.getUsuario()!=null) {
        	this.usuarioId = aviso.getUsuario().getIdUsuario();
        }
        if(aviso.getProfessor()!=null) {
        	this.professorId = aviso.getProfessor().getIdProfessor();
        }
        this.pathAnexo = aviso.getPathAnexo();
        this.permiteResposta = aviso.getPermiteResposta();
    }

    public List<Long> getDestinatarios() {
        return this.destinatario;
    }

    public void setDestinatarios(List<Long> destinatarios) {
        this.destinatario = destinatarios;
    }
}