package br.com.softsy.educacional.dto;

public class CadastroResponsavelDTO {
	
    private CadastroPessoaDTO pessoaDTO;
    private CadastroCandidatoDTO candidatoDTO;
    
	public CadastroPessoaDTO getPessoaDTO() {
		return pessoaDTO;
	}
	public void setPessoaDTO(CadastroPessoaDTO pessoaDTO) {
		this.pessoaDTO = pessoaDTO;
	}
	public CadastroCandidatoDTO getCandidatoDTO() {
		return candidatoDTO;
	}
	public void setCandidatoDTO(CadastroCandidatoDTO candidatoDTO) {
		this.candidatoDTO = candidatoDTO;
	}
    
    

}
