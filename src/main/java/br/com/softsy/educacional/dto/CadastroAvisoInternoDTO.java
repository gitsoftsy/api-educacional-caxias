package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AvisoInterno;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoInternoDTO {
	
	
    private Long idAvisoInterno;
    private Long idUsuarioEnvio;
    private Long idProfessorEnvio;
    @NotNull
    private Long tipoAvisoId;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    @NotNull
    private String titulo;
    @NotNull
    private String mensagem;
    private Long idUsuarioRecebimento;
    private Long idProfessorRecebimento;
    private LocalDateTime dataLeitura;
    private String pathAnexo;
    private Character respostasAbertas;

    public CadastroAvisoInternoDTO( AvisoInterno avisoInterno ) {
    	
    	this.idAvisoInterno = avisoInterno.getIdAvisoInterno();
        this.idUsuarioEnvio = avisoInterno.getIdUsuarioEnvio();
        this.idProfessorEnvio = avisoInterno.getIdProfessorEnvio();
        this.tipoAvisoId = avisoInterno.getTipoAviso().getIdTipoAviso();
        this.dataCadastro = avisoInterno.getDataCadastro();
        this.dataInicio = avisoInterno.getDataInicio();
        this.dataFim = avisoInterno.getDataFim();
        this.titulo = avisoInterno.getTitulo();
        this.mensagem = avisoInterno.getMensagem();
        this.idUsuarioRecebimento = avisoInterno.getIdUsuarioRecebimento();
        this.idProfessorRecebimento = avisoInterno.getIdProfessorRecebimento();
        this.dataLeitura = avisoInterno.getDataLeitura();
        this.pathAnexo = avisoInterno.getPathAnexo();
        this.respostasAbertas = avisoInterno.getRespostasAbertas();
    	
    }



}
