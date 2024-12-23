package br.com.softsy.educacional.dto;

public class CadastroResponseDTO {
	private String candidato;
	private Long idCandidato;

	public CadastroResponseDTO(String candidato, Long idCandidato) {
		this.candidato = candidato;
		this.idCandidato = idCandidato;
	}

	public String getCandidato() {
		return candidato;
	}

	public void setCandidato(String candidato) {
		this.candidato = candidato;
	}

	public Long getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(Long idCandidato) {
		this.idCandidato = idCandidato;
	}
}