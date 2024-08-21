package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Aluno;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlunoDTO {
    private Long idAluno;
    private ContaDTO conta;
    private CursoDTO curso;
    private EscolaDTO escola;
    private SerieDTO serie;
    private TurnoDTO turno;
    private PessoaDTO pessoa;
    private CandidatoDTO candidato;
    private SituacaoAlunoDTO situacaoAluno;
    private LocalDateTime dataCadastro;
    private String aluno;
    private String emailInterno;
    private String senha;

    public AlunoDTO(Aluno aluno) {
        this.idAluno = aluno.getIdAluno();
        this.conta = new ContaDTO(aluno.getConta());
        this.curso = new CursoDTO(aluno.getCurso());
        this.escola = new EscolaDTO(aluno.getEscola());
        this.serie = new SerieDTO(aluno.getSerie());
        this.turno = new TurnoDTO(aluno.getTurno());
        this.pessoa = new PessoaDTO(aluno.getPessoa());
        this.candidato = new CandidatoDTO(aluno.getCandidato());
        this.situacaoAluno = new SituacaoAlunoDTO(aluno.getSituacaoAluno());
        this.dataCadastro = aluno.getDataCadastro();
        this.aluno = aluno.getAluno();
        this.emailInterno = aluno.getEmailInterno();
        this.senha = aluno.getSenha();
    }
}