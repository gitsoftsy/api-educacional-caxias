package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.CursoSerie;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoSerieDTO {
	
	private Long idCursoSerie;
	
	private Long cursoId;
	
	private Integer serie;
	
	private String descricao;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public CursoSerieDTO(CursoSerie cursoSerie) {
		this.idCursoSerie = cursoSerie.getIdCursoSerie();
		this.cursoId = cursoSerie.getCurso().getIdCurso();
		this.serie = cursoSerie.getSerie();
		this.descricao = cursoSerie.getDescricao();
		this.dataCadastro = cursoSerie.getDataCadastro();
		this.ativo = cursoSerie.getAtivo();
	}

}
