package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.TipoIngresso;
import br.com.softsy.educacional.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CadastroCandidatoDTO {
	private Long idCandidato;
	private Long contaId;
	private Pessoa idPessoa;
	private String candidato;
	private OfertaConcurso idOfertaConcurso;
	private TipoIngresso idTipoIngresso;
	private String classificacao;
	private String idAluno;
	private String aprovado;
	private Usuario  idUsuarioAprovado;
	
public CadastroCandidatoDTO(Candidato candidato) {
		
		this.idCandidato = candidato.getIdCandidato();
		this.contaId = candidato.getConta().getIdConta();
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
