package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AvisoInterno;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroAvisoInternoDTO {
	
	
    private Long idAvisoInterno;
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

    // Listas separadas para usuários e professores
    private List<Long> destinatarioUsuario;
    private List<Long> destinatarioProfessor;

    public CadastroAvisoInternoDTO( AvisoInterno avisoInterno ) {
    	
    	this.idAvisoInterno = avisoInterno.getIdAvisoInterno();
    	 this.contaId = avisoInterno.getConta().getIdConta();
         this.tipoAvisoId = avisoInterno.getTipoAviso().getIdTipoAviso();
         this.dataCadastro = avisoInterno.getDataCadastro();
         this.dataInicio = avisoInterno.getDataInicio();
         this.dataFim = avisoInterno.getDataFim();
         this.titulo = avisoInterno.getTitulo();
         this.mensagem = avisoInterno.getMensagem();
         if(avisoInterno.getUsuario()!=null) {
         	this.usuarioId = avisoInterno.getUsuario().getIdUsuario();
         }
         if(avisoInterno.getProfessor()!=null) {
         	this.professorId = avisoInterno.getProfessor().getIdProfessor();
         }
         this.pathAnexo = avisoInterno.getPathAnexo();
         this.permiteResposta = avisoInterno.getPermiteResposta();
         this.destinatarioUsuario = destinatarioUsuario;
         this.destinatarioProfessor = destinatarioProfessor;
     }


    
}
