package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Nota;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroNotaDTO {

	private Long idNota;
    private Long alunoId;
    private Long provaId;
    private LocalDateTime dataCadastro;
    private String nota;
    private Character compareceu;
    private Integer ordem;
    
    public CadastroNotaDTO(Nota nota) {
        this.idNota = nota.getIdNota();
        this.alunoId = nota.getAluno().getIdAluno(); 
        this.provaId = nota.getProva().getIdProva();
        this.dataCadastro = nota.getDataCadastro();
        this.nota = nota.getNota();
        this.compareceu = nota.getCompareceu();
        this.ordem = nota.getOrdem();
    }
	
}
