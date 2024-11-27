package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Aluno;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class CadastroAlunoDTO {

	private Long idAluno;
	private Long contaId;
	private Long cursoId;
	private Long escolaId;
	private Long serieId;
	private Long turnoId;
	private Long pessoaId;
	private Long candidatoId;
	private Long situacaoAlunoId;
	private LocalDateTime dataCadastro;
	private String aluno;
	private String emailInterno;
	private String senha;
	private Character geraPrematricula;
	private Long tipoMatriculaId;

	public CadastroAlunoDTO(Aluno aluno) {
		this.idAluno = aluno.getIdAluno();
		this.contaId = aluno.getConta().getIdConta();
		this.cursoId = aluno.getCurso().getIdCurso();
		this.escolaId = aluno.getEscola().getIdEscola();
		this.serieId = aluno.getSerie().getIdSerie();
		this.turnoId = aluno.getTurno().getIdTurno();
		this.pessoaId = aluno.getPessoa().getIdPessoa();
		this.candidatoId = aluno.getCandidato().getIdCandidato();
		this.situacaoAlunoId = aluno.getSituacaoAluno().getIdSituacaoAluno();
		this.dataCadastro = aluno.getDataCadastro();
		this.aluno = aluno.getAluno();
		this.emailInterno = aluno.getEmailInterno();
		this.senha = aluno.getSenha();
		
		
	}
	


}
