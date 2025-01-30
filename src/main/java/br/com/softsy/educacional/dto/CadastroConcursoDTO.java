package br.com.softsy.educacional.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Concurso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroConcursoDTO {
    private Long idConcurso;
    private Long contaId;
    private String concurso;
    private Long periodoLetivoId;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private LocalDateTime dataCadastro;
    private Character ativo;
    private String pathEdital;

    public CadastroConcursoDTO(Concurso concurso) {
        this.idConcurso = concurso.getIdConcurso();
        this.contaId = concurso.getConta().getIdConta();
        this.concurso = concurso.getConcurso();
        this.periodoLetivoId = concurso.getPeriodoLetivo().getIdPeriodoLetivo();
        this.dataAbertura = concurso.getDataAbertura();
        this.dataFechamento = concurso.getDataFechamento();
        this.dataCadastro = concurso.getDataCadastro();
        this.ativo = concurso.getAtivo();
        this.pathEdital = concurso.getPathEdital();
    }
}