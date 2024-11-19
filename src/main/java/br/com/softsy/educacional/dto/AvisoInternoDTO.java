package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.AvisoInterno;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoInternoDTO {
	
    private Long idAvisoInterno;
    private Long idUsuarioEnvio;
    private Long idProfessorEnvio;
    private TipoAvisoDTO tipoAviso;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String titulo;
    private String mensagem;
    private Long idUsuarioRecebimento;
    private Long idProfessorRecebimento;
    private LocalDateTime dataLeitura;
    private String pathAnexo;
    private Character respostasAbertas;

    public AvisoInternoDTO( AvisoInterno avisoInterno ) {
    	
    	this.idAvisoInterno = avisoInterno.getIdAvisoInterno();
        this.idUsuarioEnvio = avisoInterno.getIdUsuarioEnvio();
        this.idProfessorEnvio = avisoInterno.getIdProfessorEnvio();
        this.tipoAviso = new TipoAvisoDTO(avisoInterno.getTipoAviso());
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
