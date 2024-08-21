package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.AgendaAnexo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgendaAnexoDTO {

    private Long idAgendaAnexo;

    @NotNull
    private Long agendaId;

    private LocalDateTime dataCadastro;

    private String caminhoArquivo;

    private Character ativo;

    public AgendaAnexoDTO(AgendaAnexo agendaAnexo) {
        this.idAgendaAnexo = agendaAnexo.getIdAgendaAnexo();
        this.agendaId = agendaAnexo.getAgenda().getIdAgenda();
        this.dataCadastro = agendaAnexo.getDataCadastro();
        this.caminhoArquivo = agendaAnexo.getCaminhoArquivo();
        this.ativo = agendaAnexo.getAtivo();
    }
}