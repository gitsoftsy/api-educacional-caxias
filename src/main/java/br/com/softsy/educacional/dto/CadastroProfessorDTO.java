package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Professor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroProfessorDTO {

    private Long idProfessor;
    private Long contaId;
    private Long pessoaId;
    private String codigoInep;
    private String matricula;
    private String usuario;
    private String senha;
    private LocalDateTime dataCadastro;
    private Character ativo;
    private String emailInstitucional;
    private LocalDate dataContratacao;
    private LocalDate dataDemissao;
    
    public CadastroProfessorDTO(Professor professor) {
        this.idProfessor = professor.getIdProfessor();
        this.contaId = professor.getConta().getIdConta();
        this.pessoaId = professor.getPessoa().getIdPessoa();
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