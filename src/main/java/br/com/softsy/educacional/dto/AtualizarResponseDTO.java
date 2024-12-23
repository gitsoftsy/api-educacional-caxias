package br.com.softsy.educacional.dto;

public class AtualizarResponseDTO {
	private Long idPessoa;
	private Long idCandidato;

	public AtualizarResponseDTO(Long idPessoa, Long idCandidato) {
		this.idPessoa = idPessoa;
		this.idCandidato = idCandidato;
	}

	// Getters e Setters
	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(Long idCandidato) {
		this.idCandidato = idCandidato;
	}
}
