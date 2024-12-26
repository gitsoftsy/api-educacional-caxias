package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Nota;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotaDTO {

    private Long idNota;
    private Long alunoId;
    private String aluno;
    private String nomeAluno;
    private ProvaDTO prova;
    private LocalDateTime dataCadastro;
    private String nota;
    private Character compareceu;
    private Integer ordem;

    public NotaDTO(Nota nota) {
        this.idNota = nota.getIdNota();
        this.alunoId = nota.getAluno().getIdAluno();
        this.aluno = nota.getAluno().getAluno();
        this.nomeAluno = nota.getAluno().getPessoa().getNomeCompleto();
        this.prova = new ProvaDTO(nota.getProva());
        this.dataCadastro = nota.getDataCadastro();
        this.nota = nota.getNota();
        this.compareceu = nota.getCompareceu();
        this.ordem = nota.getOrdem();
    }
}