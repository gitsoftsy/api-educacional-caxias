package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Modulo;
import br.com.softsy.educacional.model.TipoAviso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoAvisoDTO {
	
	private Long idTipoAviso;

	private Long contaId;
	
	private String descricao;
	
    public TipoAvisoDTO(TipoAviso tipoAviso) {
        this.idTipoAviso = tipoAviso.getIdTipoAviso();
        this.contaId = tipoAviso.getConta().getIdConta();
        this.descricao = tipoAviso.getDescricao();
    }

}
