package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Aviso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoDTO {

    private Long idAviso;
    private Long aluno;
    private TipoAvisoDTO tipoAviso;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String titulo;
    private String mensagem;
    private UsuarioDTO usuario;
    private ProfessorDTO professor;
    private LocalDateTime dataLeitura;
    private String pathAnexo;
    private Character respostasAbertas;

    public AvisoDTO(Aviso aviso) {
        this.idAviso = aviso.getIdAviso();
        this.aluno = aviso.getAluno().getIdAluno();
		
        this.tipoAviso = new TipoAvisoDTO(aviso.getTipoAviso());
        this.dataCadastro = aviso.getDataCadastro();
        this.dataInicio = aviso.getDataInicio();
        this.dataFim = aviso.getDataFim();
        this.titulo = aviso.getTitulo();
        this.mensagem = aviso.getMensagem();

        
		if (aviso.getUsuario() != null) {
			this.usuario = new UsuarioDTO(aviso.getUsuario());
		} else {
			this.usuario = null;
		}
        
        
		if (aviso.getProfessor() != null) {
			this.professor = new ProfessorDTO(aviso.getProfessor());
		} else {
			this.professor = null;
		}
        
        this.dataLeitura = aviso.getDataLeitura();
        this.pathAnexo = aviso.getPathAnexo();
        this.respostasAbertas = aviso.getRespostasAbertas();
    }
}