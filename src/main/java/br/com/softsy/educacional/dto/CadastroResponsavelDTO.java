package br.com.softsy.educacional.dto;

public class CadastroResponsavelDTO {
	
    private CadastroPessoaDTO pessoaDTO;
    private CadastroCandidatoRelacionamentoDTO candidatoRelacionamentoDTO;
    
	public CadastroPessoaDTO getPessoaDTO() {
		return pessoaDTO;
	}
	public void setPessoaDTO(CadastroPessoaDTO pessoaDTO) {
		this.pessoaDTO = pessoaDTO;
	}
	public CadastroCandidatoRelacionamentoDTO getCandidatoRelacionamentoDTO() {
		return candidatoRelacionamentoDTO;
	}
	public void setCandidatoRelacionamentoDTO(CadastroCandidatoRelacionamentoDTO candidatoRelacionamentoDTO) {
		this.candidatoRelacionamentoDTO = candidatoRelacionamentoDTO;
	}
    
    

}
