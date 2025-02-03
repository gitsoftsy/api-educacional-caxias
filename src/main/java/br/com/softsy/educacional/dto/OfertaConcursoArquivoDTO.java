package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.OfertaConcursoArquivo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfertaConcursoArquivoDTO {

	private Long idOfertaConcursoArq;

	private Long contaId;

	private Long ofertaConcursoId;

	private Long usuarioId;

	private LocalDateTime dataCadastro;

	private String pathArq;

	private String  nomeArq;

	private Integer ordem;
	
	public OfertaConcursoArquivoDTO(OfertaConcursoArquivo ofertaConcursoArquivo) {
		
		this.idOfertaConcursoArq = ofertaConcursoArquivo.getIdOfertaConcursoArq();
		this.contaId = ofertaConcursoArquivo.getConta().getIdConta();
        this.ofertaConcursoId = ofertaConcursoArquivo.getOfertaConcurso().getIdOfertaConcurso();
        this.usuarioId = ofertaConcursoArquivo.getUsuario().getIdUsuario();
        this.dataCadastro = ofertaConcursoArquivo.getDataCadastro();
        this.pathArq = ofertaConcursoArquivo.getPathArq();
        this.nomeArq = ofertaConcursoArquivo.getNomeArq();
        this.ordem = ofertaConcursoArquivo.getOrdem();
	}


}
