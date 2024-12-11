package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.NotaLog;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotaLogDTO {
	
	 private Long idNotaLog;
	 private Long aluno;
	 private Long prova;
	 private Long nota;
	 private LocalDateTime dataCadastro;
	 private Character operacao;
	 private String notaAnterior;
	 private String notaAtual;
	 private Long professor;
	 private Long usuario;

	 public NotaLogDTO(NotaLog notaLog) {
		 
		this.idNotaLog = notaLog.getIdNotaLog();
		this.aluno = notaLog.getAluno().getIdAluno();
		this.prova = notaLog.getProva().getIdProva();
	    this.nota = notaLog.getNota().getIdNota();
	    this.dataCadastro = notaLog.getDataCadastro();
	    this.operacao = notaLog.getOperacao();
	    this.notaAnterior = notaLog.getNotaAnterior();
	    this.notaAtual = notaLog.getNotaAtual();
		this.usuario = notaLog.getUsuario() != null ? notaLog.getUsuario().getIdUsuario() : null;
		this.professor = notaLog.getProfessor() != null ? notaLog.getProfessor().getIdProfessor() : null;
	    
		 
	 }
}
