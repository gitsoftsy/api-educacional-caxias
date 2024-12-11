package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.NotaLog;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CadastroNotaLogDTO {
	
	 private Long idNotaLog;
	 private Long alunoId;
	 @NotNull
	 private Long prova;
	 @NotNull
	 private Long nota;
	 private LocalDateTime dataCadastro;
	 private Character operacao;
	 private String notaAnterior;
	 private String notaAtual;
	 private Long professorId;
	 private Long usuarioId;

	 public CadastroNotaLogDTO(NotaLog notaLog) {
		 
			this.idNotaLog = notaLog.getIdNotaLog();
			this.alunoId = notaLog.getAluno().getIdAluno(); 
			this.prova = notaLog.getProva().getIdProva();
		    this.nota = notaLog.getNota().getIdNota();
		    this.dataCadastro = notaLog.getDataCadastro();
		    this.operacao = notaLog.getOperacao();
		    this.notaAnterior = notaLog.getNotaAnterior();
		    this.notaAtual = notaLog.getNotaAtual();
			this.professorId = notaLog.getProfessor()!= null ? notaLog.getProfessor().getIdProfessor() : null;
			this.usuarioId = notaLog.getUsuario()!= null ? notaLog.getUsuario().getIdUsuario() : null;
		    
			 
		 }
}
