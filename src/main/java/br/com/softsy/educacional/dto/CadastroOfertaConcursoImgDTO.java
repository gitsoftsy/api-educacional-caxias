package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.OfertaConcursoImg;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroOfertaConcursoImgDTO {
	private Long idOfertaConcursoImg;

	private Long contaId;

	private Long ofertaConcursoId;

	private Long usuarioId;

	private LocalDateTime dataCadastro;

	@NotNull
	private String tipoDispositivo; // Tipo do dispositivo ('D' ou 'M')

	@NotNull
	private String pathImg;

	@NotNull
	private Integer ordem;

	private String url;

	public CadastroOfertaConcursoImgDTO(OfertaConcursoImg OfertaConcursoImg) {
		this.idOfertaConcursoImg = OfertaConcursoImg.getIdOfertaConcursoImg();
		this.contaId = OfertaConcursoImg.getConta().getIdConta();
		this.ofertaConcursoId = OfertaConcursoImg.getOfertaConcurso().getIdOfertaConcurso();
		this.usuarioId = OfertaConcursoImg.getUsuario().getIdUsuario();
		this.dataCadastro = OfertaConcursoImg.getDataCadastro();
		this.tipoDispositivo = OfertaConcursoImg.getTipoDispositivo();
		this.pathImg = OfertaConcursoImg.getPathImg();
		this.ordem = OfertaConcursoImg.getOrdem();
		this.url = OfertaConcursoImg.getUrl();
	}
}
