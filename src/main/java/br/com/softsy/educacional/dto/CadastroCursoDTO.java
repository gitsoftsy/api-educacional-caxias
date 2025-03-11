package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.Curso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroCursoDTO {
    private Long idCurso;
    
    
    @NotNull
    private String codCurso;
    
    @NotNull
    private String nome;
    
    @NotNull
    private String codCursoInpe;
    
    private LocalDateTime dataCadastro;
    
    private Character ativo;
    
    @NotNull
    private Long contaId;
    
    public CadastroCursoDTO(Curso curso) {
        this.idCurso = curso.getIdCurso();
        this.codCurso = curso.getCodCurso();
        this.nome = curso.getNome();
        this.codCursoInpe = curso.getCodCursoInpe();
        this.dataCadastro = curso.getDataCadastro();
        this.ativo = curso.getAtivo();
        this.contaId = curso.getConta().getIdConta();
    }
}
