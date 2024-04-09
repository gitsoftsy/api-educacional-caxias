package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Disciplina;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroDisciplinaDTO {
    private Long idDisciplina;
    
    @NotNull
    private Long dependenciaAdmId;
    
    private Long escolaId;
    
    @NotNull
    private String disciplina;
    
    @NotNull
    private String nome;
    
    private Double creditos;
    
    private Double horasAula;
    
    private Double horasEstagio;
    
    private Double horasAtiv;
    
    private LocalDateTime dataCadastro;
    
    private Character ativo;
    
    public CadastroDisciplinaDTO(Disciplina disciplina) {
        this.idDisciplina = disciplina.getIdDisciplina();
        this.dependenciaAdmId = disciplina.getDependenciaAdm().getIdDependenciaAdministrativa();
        this.escolaId = disciplina.getEscola().getIdEscola();
        this.disciplina = disciplina.getDisciplina();
        this.nome = disciplina.getNome();
        this.creditos = disciplina.getCreditos();
        this.horasAula = disciplina.getHorasAula();
        this.horasEstagio = disciplina.getHorasEstagio();
        this.horasAtiv = disciplina.getHorasAtiv();
        this.dataCadastro = disciplina.getDataCadastro();
        this.ativo = disciplina.getAtivo();
    }
}
