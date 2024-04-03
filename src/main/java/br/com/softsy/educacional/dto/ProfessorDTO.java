package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Professor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfessorDTO {

    private Long idProfessor;
    private PessoaDTO pessoa;
    private String codigoInep;
    private String matricula;
    private SituacaoProfessorDTO situacaoProfessor;
    private Character deficiente;
    private Character autista;
    private Character altasHabilidades;
    private LocalDateTime dataCadastro;
    private NivelEscolaridadeDTO nivelEscolaridade;
    private TipoEnsinoMedioDTO tipoEnsinoMedio;
    private Character ativo;

    public ProfessorDTO(Professor professor) {
        this.idProfessor = professor.getIdProfessor();
        this.pessoa = new PessoaDTO(professor.getPessoa());
        this.codigoInep = professor.getCodigoInep();
        this.matricula = professor.getMatricula();
        this.situacaoProfessor = new SituacaoProfessorDTO(professor.getSituacaoProfessor());
        this.deficiente = professor.getDeficiente();
        this.autista = professor.getAutista();
        this.altasHabilidades = professor.getAltasHabilidades();
        this.dataCadastro = professor.getDataCadastro();
        this.nivelEscolaridade = new NivelEscolaridadeDTO(professor.getNivelEscolaridade());
        this.tipoEnsinoMedio = new TipoEnsinoMedioDTO(professor.getTipoEnsinoMedio());
        this.ativo = professor.getAtivo();
    }
}