package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Prova;
import br.com.softsy.educacional.model.Turma;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProvaDTO {

	private Long  idProva;
	private TurmaDTO turma;
	private LocalDateTime dataCadastro;
	private String nomeAbreviado;
	private String descricao;
	private LocalDate dataDivulgacao;
	private LocalDateTime dataAgendaProva;
	private Character ativo;
	private Integer ordem;
	private Character tipoConceito;
	private String conceitoMax;
	private LocalDate dataLimiteRevisao;
	private Character ehSimulado;
	private String formula;
	
	public ProvaDTO(Prova prova) {
		
	  this.idProva = prova.getIdProva();
	  this.turma = new TurmaDTO(prova.getTurma()); 
	  this.dataCadastro = prova.getDataCadastro();
	  this.nomeAbreviado = prova.getNomeAbreviado();
	  this.descricao = prova.getDescricao();
	  this.dataDivulgacao = prova.getDataDivulgacao();
	  this.dataAgendaProva = prova.getDataAgendaProva();
	  this.ativo = prova.getAtivo();
	  this.ordem = prova.getOrdem();
	  this.tipoConceito = prova.getTipoConceito();
	  this.conceitoMax = prova.getConceitoMax();
	  this.dataLimiteRevisao = prova.getDataLimiteRevisao();
	  this.ehSimulado = prova.getEhSimulado();
	  this.formula = prova.getFormula(); 			
		
		
	}
	
	
	
}
