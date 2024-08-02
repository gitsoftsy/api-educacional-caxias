package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Professor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessorDTO {

    private Long idProfessor;
    private ContaDTO conta;
    private PessoaDTO pessoa;
    private String codigoInep;
    private String matricula;
    private String usuario;
    private String senha;
    private LocalDateTime dataCadastro;
    private Character ativo;
    private String emailInstitucional;
    private LocalDate dataContratacao;
    private LocalDate dataDemissao;
    
    public ProfessorDTO(Professor professor) {
        this.idProfessor = professor.getIdProfessor();
        this.conta = new ContaDTO(professor.getConta());
        this.pessoa = new PessoaDTO(professor.getPessoa());
        this.codigoInep = professor.getCodigoInep();
        this.matricula = professor.getMatricula();
        this.usuario = professor.getUsuario();
        this.senha = professor.getSenha();
        this.dataCadastro = professor.getDataCadastro();
        this.ativo = professor.getAtivo();
        this.emailInstitucional = professor.getEmailInstitucional();
        this.dataContratacao = professor.getDataContratacao();
        this.dataDemissao = professor.getDataDemissao();
    }
}