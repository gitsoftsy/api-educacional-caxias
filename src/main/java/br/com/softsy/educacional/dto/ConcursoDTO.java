package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Concurso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConcursoDTO {
    private Long idConcurso;
    private ContaDTO conta;
    private String concurso;
    private PeriodoLetivoDTO periodoLetivo;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public ConcursoDTO(Concurso concurso) {
        this.idConcurso = concurso.getIdConcurso();
        this.conta = new ContaDTO(concurso.getConta());
        this.concurso = concurso.getConcurso();
        this.periodoLetivo = new PeriodoLetivoDTO(concurso.getPeriodoLetivo());
        this.dataAbertura = concurso.getDataAbertura();
        this.dataFechamento = concurso.getDataFechamento();
        this.dataCadastro = concurso.getDataCadastro();
        this.ativo = concurso.getAtivo();
    }
}