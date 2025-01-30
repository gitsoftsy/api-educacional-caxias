package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.CursoDescr;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroCursoDescrDTO {

	private Long idCursoDescr;
	private Long contaId;
	private Long cursoId;
	private Long usuarioId;
	private LocalDateTime dataCadastro;
	private String titulo;
	private String descricao;
	private Integer ordem;

	public CadastroCursoDescrDTO(CursoDescr cursoDescr) {
		this.idCursoDescr = cursoDescr.getIdCursoDescr();
		this.contaId = cursoDescr.getConta().getIdConta();
		this.cursoId = cursoDescr.getCurso().getIdCurso();
		this.usuarioId = cursoDescr.getUsuario().getIdUsuario();
		this.dataCadastro = cursoDescr.getDataCadastro();
		this.titulo = cursoDescr.getTitulo();
		this.descricao = cursoDescr.getDescricao();
		this.ordem = cursoDescr.getOrdem();
	}

}
