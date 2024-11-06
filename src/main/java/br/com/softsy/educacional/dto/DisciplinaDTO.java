package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Disciplina;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisciplinaDTO {
	private Long idDisciplina;

	private ContaDTO conta;

	private Long areaConhecimentoId;
	
	private String nomeAreaConhecimento;

	private String codDiscip;
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

	public DisciplinaDTO(Disciplina disciplina) {
		this.idDisciplina = disciplina.getIdDisciplina();
		this.conta = new ContaDTO(disciplina.getConta());
		this.areaConhecimentoId = disciplina.getAreaConhecimento().getIdAreaConhecimento();
		this.nomeAreaConhecimento = disciplina.getAreaConhecimento().getAreaConhecimento();
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
