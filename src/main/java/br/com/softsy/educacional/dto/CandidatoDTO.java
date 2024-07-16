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
	private Long pessoa;
	private String candidato;
	private OfertaConcursoDTO ofertaConcurso;
	private TipoIngressoDTO tipoIngresso;
	private String classificacao;
	private Long aluno;
	private Character aprovado;
	private UsuarioDTO usuarioAprovacao;
	
	public CandidatoDTO(Candidato candidato) {
		
		this.idCandidato = candidato.getIdCandidato();
		this.conta = new ContaDTO(candidato.getConta());
		this.pessoa = candidato.getPessoa().getIdPessoa();
		this.candidato = candidato.getCandidato();
		this.ofertaConcurso = new OfertaConcursoDTO (candidato.getOfertaConcurso());
		this.tipoIngresso = new TipoIngressoDTO(candidato.getTipoIngresso());
		this.classificacao = candidato.getClassificacao();
		this.aluno = candidato.getAluno();
		this.aprovado = candidato.getAprovado();
		
		if (candidato.getUsuarioAprovacao() != null) {
			this.usuarioAprovacao = new UsuarioDTO(candidato.getUsuarioAprovacao());
		} else {
			this.usuarioAprovacao = null;
		}
	
		
	}

}
