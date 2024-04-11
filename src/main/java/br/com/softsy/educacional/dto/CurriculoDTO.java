package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Curriculo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurriculoDTO {
	
	private Long idCurriculo;
	
	@NotNull
	private Long cursoId;
	
	@NotNull
	private String curriculo;
	
	private LocalDateTime dtHomologacao;
	
	private LocalDateTime dtExtincao;
	
	private Integer prazoIdeal;
	
	private Integer prazoMax;
	
	private Double creditos;
	
	private Integer aulasPrevistas;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public CurriculoDTO(Curriculo curriculo) {
		this.idCurriculo = curriculo.getIdCurriculo();
		this.cursoId = curriculo.getCurso().getIdCurso();
		this.curriculo = curriculo.getCurriculo();
		this.dtHomologacao = curriculo.getDtHomologacao();
		this.dtExtincao = curriculo.getDtExtincao();
		this.prazoIdeal = curriculo.getPrazoIdeal();
		this.prazoMax = curriculo.getPrazoMax();
		this.creditos = curriculo.getCreditos();
		this.aulasPrevistas = curriculo.getAulasPrevistas();
		this.dataCadastro = curriculo.getDataCadastro();
		this.ativo = curriculo.getAtivo();
	}

}
