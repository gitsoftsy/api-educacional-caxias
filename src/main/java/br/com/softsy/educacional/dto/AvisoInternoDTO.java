package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.AvisoInterno;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvisoInternoDTO {
	
    private Long idAvisoInterno;
    private Long idConta;
    private TipoAvisoDTO tipoAviso;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String titulo;
    private String mensagem;
    private UsuarioDTO usuario;
    private ProfessorDTO professor;
    private String pathAnexo;
    private Character permiteResposta;

    public AvisoInternoDTO( AvisoInterno avisoInterno ) {
    	
    	this.idAvisoInterno = avisoInterno.getIdAvisoInterno();
    	this.idConta = avisoInterno.getConta().getIdConta();
        this.tipoAviso = new TipoAvisoDTO(avisoInterno.getTipoAviso());
        this.dataCadastro = avisoInterno.getDataCadastro();
        this.dataInicio = avisoInterno.getDataInicio();
        this.dataFim = avisoInterno.getDataFim();
        this.titulo = avisoInterno.getTitulo();
        this.mensagem = avisoInterno.getMensagem();

        
		if (avisoInterno.getUsuario() != null) {
			this.usuario = new UsuarioDTO(avisoInterno.getUsuario());
		} else {
			this.usuario = null;
		}
        
        
		if (avisoInterno.getProfessor() != null) {
			this.professor = new ProfessorDTO(avisoInterno.getProfessor());
		} else {
			this.professor = null;
		}
        
        this.pathAnexo = avisoInterno.getPathAnexo();
        this.permiteResposta = avisoInterno.getPermiteResposta();
    }
    

}
