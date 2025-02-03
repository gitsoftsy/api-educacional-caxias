package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Utm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UtmDTO {
    private Long idUtm;
    private Long contaId;
    private String utmNome;
    private String descricao;
    private LocalDateTime dataCadastro;
    private Character ativo;
    
    public UtmDTO(Utm utm) {
        this.idUtm = utm.getIdUtm();
        this.contaId = utm.getConta().getIdConta();
        this.utmNome = utm.getUtmNome();
        this.descricao = utm.getDescricao();
        this.dataCadastro = utm.getDataCadastro();
        this.ativo = utm.getAtivo();
    }
}