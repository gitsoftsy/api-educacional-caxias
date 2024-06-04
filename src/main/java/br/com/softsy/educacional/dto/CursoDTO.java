package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Curso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoDTO {
    private Long idCurso;
    
    private String codCurso;
    
    private String nome;
    
    private String codCursoInpe;
    
    private LocalDateTime dataCadastro;
    
    private Character ativo;
    
    private ContaDTO conta;
    
    public CursoDTO(Curso curso) {
        this.idCurso = curso.getIdCurso();
        this.codCurso = curso.getCodCurso();
        this.nome = curso.getNome();
        this.codCursoInpe = curso.getCodCursoInpe();
        this.dataCadastro = curso.getDataCadastro();
        this.ativo = curso.getAtivo();
        this.conta = new ContaDTO(curso.getConta());
    }
}