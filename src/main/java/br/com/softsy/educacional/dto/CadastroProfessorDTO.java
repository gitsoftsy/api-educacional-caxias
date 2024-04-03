package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Professor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroProfessorDTO {

    private Long idProfessor;
    @NotNull
    private Long pessoaId;
    @NotNull
    private String codigoInep;
    @NotNull
    private String matricula;
    @NotNull
    private Long situacaoProfessorId;
    @NotNull
    private Character deficiente;
    @NotNull
    private Character autista;
    @NotNull
    private Character altasHabilidades;

    private LocalDateTime dataCadastro;
    @NotNull
    private Long nivelEscolaridadeId;
    @NotNull
    private Long tipoEnsinoMedioId;
    private Character ativo;

    public CadastroProfessorDTO(Professor professor) {
        this.idProfessor = professor.getIdProfessor();
        this.pessoaId = professor.getPessoa().getIdPessoa();
        this.codigoInep = professor.getCodigoInep();
        this.matricula = professor.getMatricula();
        this.situacaoProfessorId = professor.getSituacaoProfessor().getIdSituacaoProfessor();
        this.deficiente = professor.getDeficiente();
        this.autista = professor.getAutista();
        this.altasHabilidades = professor.getAltasHabilidades();
        this.dataCadastro = professor.getDataCadastro();
        this.nivelEscolaridadeId = professor.getNivelEscolaridade().getIdNivelEscolaridade();
        this.tipoEnsinoMedioId = professor.getTipoEnsinoMedio().getIdTipoEnsinoMedio();
        this.ativo = professor.getAtivo();
    }
}