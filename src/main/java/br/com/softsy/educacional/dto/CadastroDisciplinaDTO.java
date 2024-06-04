package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Disciplina;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroDisciplinaDTO {
	private Long idDisciplina;

	@NotNull
	private Long contaId;

	private Long areaConhecimentoId;

	@NotNull
	private String codDiscip;

	@NotNull
	private String nome;

	private Double creditos;

	private Double horasAula;

	private Double horasEstagio;

	private Double horasAtiv;

	private Double horasLab;
	
	private Double horasAno;
	
	private Double horasSemanal;

	private LocalDateTime dataCadastro;

	private Character ativo;

	public CadastroDisciplinaDTO(Disciplina disciplina) {
		this.idDisciplina = disciplina.getIdDisciplina();
		this.areaConhecimentoId = disciplina.getAreaConhecimento().getIdAreaConhecimento();
		this.codDiscip = disciplina.getCodDiscip();
		this.nome = disciplina.getNome();
		this.creditos = disciplina.getCreditos();
		this.horasAula = disciplina.getHorasAula();
		this.horasEstagio = disciplina.getHorasEstagio();
		this.horasAtiv = disciplina.getHorasAtiv();
		this.horasLab = disciplina.getHorasLab();
		this.horasAno = disciplina.getHorasAno();
		this.horasSemanal = disciplina.getHorasSemanal();
		this.dataCadastro = disciplina.getDataCadastro();
		this.ativo = disciplina.getAtivo();
	}
}
