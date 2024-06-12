package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.TipoIngresso;
import br.com.softsy.educacional.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidatoDTO {
	private Long idCandidato;
	private ContaDTO conta;
	private Pessoa idPessoa;
	private String candidato;
	private OfertaConcurso idOfertaConcurso;
	private TipoIngresso idTipoIngresso;
	private String classificacao;
	private String idAluno;
	private String aprovado;
	private Usuario  idUsuarioAprovado;
	
	public CandidatoDTO(Candidato candidato) {
		
		this.idCandidato = candidato.getIdCandidato();
		this.conta = new ContaDTO(candidato.getConta());
		this.idPessoa = candidato.getIdPessoa();
		this.candidato = candidato.getCandidato();
		this.idOfertaConcurso = candidato.getIdOfertaConcurso();
		this.idTipoIngresso = candidato.getIdTipoIngresso();
		this.classificacao = candidato.getClassificacao();
		this.idAluno = candidato.getIdAluno();
		this.aprovado = candidato.getAprovado();
		this.idUsuarioAprovado = candidato.getIdUsuarioAprovado();
	
		
	}

}
