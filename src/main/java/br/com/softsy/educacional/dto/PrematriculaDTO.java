package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.softsy.educacional.model.Prematricula;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrematriculaDTO {
	
	 private Long idPrematricula;
	 private Long idConta;
	 private Character ativo;
	 private LocalDateTime dataCadastro;
	 private Long idTipoMatricula;
	 private Long idAluno;
	 private Long idPeriodoLetivo;
	 private Long idDisciplina;
	 private Long idTurma;
	 private Long idSerie;
	 private String mensagemErro;
	 private Character manual;
	 private Long idUsuario;
	 private LocalDateTime dataAtualizacao;
	 private LocalDateTime dataConfirmacao;
	 private String observacao;

	 public PrematriculaDTO(Prematricula prematricula){
		 
		 	this.idPrematricula = prematricula.getIdPrematricula();
	        this.idConta = prematricula.getConta().getIdConta();
	        this.ativo = prematricula.getAtivo();
	        this.dataCadastro = prematricula.getDataCadastro();
	        this.idTipoMatricula = prematricula.getTipoMatricula().getIdTipoMatricula();
	        this.idAluno = prematricula.getAluno().getIdAluno();
	        this.idPeriodoLetivo = prematricula.getPeriodoLetivo().getIdPeriodoLetivo();
	        this.idDisciplina = prematricula.getDisciplina().getIdDisciplina();
			this.idTurma = prematricula.getTurma()!= null ? prematricula.getTurma().getIdTurma() : null;
	        this.idSerie = prematricula.getSerie()!= null ? prematricula.getSerie().getIdSerie() : null;
	        this.mensagemErro = prematricula.getMensagemErro();
	        this.manual = prematricula.getManual();
	        this.idUsuario = prematricula.getUsuario()!= null ? prematricula.getUsuario().getIdUsuario() : null;
	        this.dataAtualizacao = prematricula.getDataAtualizacao();
	        this.dataConfirmacao = prematricula.getDataConfirmacao();
	        this.observacao = prematricula.getObservacao();
		 
	 }
}
