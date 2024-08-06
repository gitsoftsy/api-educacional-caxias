package br.com.softsy.educacional.dto;

public class CadastroPessoaProfessorDTO {
    private CadastroPessoaDTO pessoaDTO;
    private CadastroProfessorDTO professorDTO;

    public CadastroPessoaDTO getPessoaDTO() {
        return pessoaDTO;
    }

    public void setPessoaDTO(CadastroPessoaDTO pessoaDTO) {
        this.pessoaDTO = pessoaDTO;
    }

    public CadastroProfessorDTO getProfessorDTO() {
        return professorDTO;
    }

    public void setProfessorDTO(CadastroProfessorDTO professorDTO) {
        this.professorDTO = professorDTO;
    }
}