package br.com.softsy.educacional.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Aviso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoDTO {

    private Long idAviso;
    private Long idConta;
    private TipoAvisoDTO tipoAviso;
    private LocalDateTime dataCadastro;
    private Date dataInicio;
    private Date dataFim;
    private String titulo;
    private String mensagem;
    private UsuarioDTO usuario;
    private ProfessorDTO professor;
    private String pathAnexo;
    private Character permiteResposta;

    public AvisoDTO(Aviso aviso) {
    		this.idAviso = aviso.getIdAviso();
    	    this.idConta = (aviso.getConta() != null) ? aviso.getConta().getIdConta() : null;
    	    this.tipoAviso = (aviso.getTipoAviso() != null) ? new TipoAvisoDTO(aviso.getTipoAviso()) : null;
    	    this.dataCadastro = aviso.getDataCadastro();
    	    this.dataInicio = aviso.getDataInicio() != null ? new Date(aviso.getDataInicio().getTime()) : null;
    	    this.dataFim = aviso.getDataFim() != null ? new Date(aviso.getDataFim().getTime()) : null;
    	    this.titulo = aviso.getTitulo();
    	    this.mensagem = aviso.getMensagem();
    	    this.usuario = (aviso.getUsuario() != null) ? new UsuarioDTO(aviso.getUsuario()) : null;
    	    this.professor = (aviso.getProfessor() != null) ? new ProfessorDTO(aviso.getProfessor()) : null;
    	    this.pathAnexo = aviso.getPathAnexo();
    	    this.permiteResposta = aviso.getPermiteResposta();
    }
}