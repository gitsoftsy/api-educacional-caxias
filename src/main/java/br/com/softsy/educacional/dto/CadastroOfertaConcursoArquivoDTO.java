package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.OfertaConcursoArquivo;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CadastroOfertaConcursoArquivoDTO {
	private Long idOfertaConcursoArq;

	@NotNull
	private Long contaId;

	@NotNull
	private Long ofertaConcursoId;

	@NotNull
	private Long usuarioId;

	private LocalDateTime dataCadastro;

	@NotNull
	private String pathArq;

	@NotNull
	private String  nomeArq;

	@NotNull
	private Integer ordem;
	
	public CadastroOfertaConcursoArquivoDTO(OfertaConcursoArquivo ofertaConcursoArquivo) {
		
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
