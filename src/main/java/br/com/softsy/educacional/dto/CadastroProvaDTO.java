package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Prova;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroProvaDTO {

	private Long  idProva;
    @NotNull
	private Long turmaId;
	private LocalDateTime dataCadastro;
	@NotNull
	private String nomeAbreviado;
	@NotNull
	private String descricao;
	private Date dataDivulgacao;
	private LocalDateTime dataAgendaProva;
	@NotNull
	private Character ativo;
	private Integer ordem;
	@NotNull
	private Character tipoConceito;
	private String conceitoMax;
	private LocalDate dataLimiteRevisao;
	@NotNull
	private Character ehSimulado;
	private String formula;
	
	public CadastroProvaDTO(Prova prova) {
		
		  this.idProva = prova.getIdProva();
		  this.turmaId = prova.getTurma().getIdTurma();
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
