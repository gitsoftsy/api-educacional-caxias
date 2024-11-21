package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Prematricula;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroPrematriculaDTO {
	

	 private Long idPrematricula;
   	 @NotNull
	 private Long contaId;
     @NotNull
	 private Character ativo;
	 private LocalDateTime dataCadastro;
	 @NotNull
	 private Long tipoMatriculaId;
	 @NotNull
	 private Long alunoId;
	 @NotNull
	 private Long periodoLetivoId;
	 @NotNull
	 private Long disciplinaId;
	 private Long turmaId;
	 private Long serieId;
	 private String mensagemErro;
	 @NotNull
	 private Character manual;
	 private Long usuarioId;
	 private LocalDateTime dataAtualizacao;
	 private LocalDateTime dataConfirmacao;
	 private String observacao;
	 

	 public CadastroPrematriculaDTO(Prematricula prematricula){
		 
		 	this.idPrematricula = prematricula.getIdPrematricula();
	        this.contaId = prematricula.getConta().getIdConta();
	        this.ativo = prematricula.getAtivo();
	        this.dataCadastro = prematricula.getDataCadastro();
	        this.tipoMatriculaId = prematricula.getTipoMatricula().getIdTipoMatricula();
	        this.alunoId = prematricula.getAluno().getIdAluno();
	        this.periodoLetivoId = prematricula.getPeriodoLetivo().getIdPeriodoLetivo();
	        this.disciplinaId = prematricula.getDisciplina().getIdDisciplina();
	        this.turmaId = prematricula.getTurma().getIdTurma();
	        this.serieId = prematricula.getSerie().getIdSerie();
	        this.mensagemErro = prematricula.getMensagemErro();
	        this.manual = prematricula.getManual();
	        this.usuarioId = prematricula.getUsuario().getIdUsuario();
	        this.dataAtualizacao = prematricula.getDataAtualizacao();
	        this.dataConfirmacao = prematricula.getDataConfirmacao();
	        this.observacao = prematricula.getObservacao();
		 
	 }

}
