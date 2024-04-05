package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.ProfessorFormacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroProfessorFormacaoDTO {

    private Long idProfessorFormacao;
    private Long professorId;
    private Long modalidadeEscolaId;
    private String nomeCurso;
    private String ies;
    private Integer anoConclusao;
    private String certificado;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public CadastroProfessorFormacaoDTO(ProfessorFormacao professorFormacao) {
        this.idProfessorFormacao = professorFormacao.getIdProfessorFormacao();
        this.professorId = professorFormacao.getProfessor().getIdProfessor();
        this.modalidadeEscolaId = professorFormacao.getModalidadeEscola().getIdModalidadeEscola();
        this.nomeCurso = professorFormacao.getNomeCurso();
        this.ies = professorFormacao.getIes();
        this.anoConclusao = professorFormacao.getAnoConclusao();
        this.certificado = professorFormacao.getCertificado();
        this.dataCadastro = professorFormacao.getDataCadastro();
        this.ativo = professorFormacao.getAtivo();
    }
}