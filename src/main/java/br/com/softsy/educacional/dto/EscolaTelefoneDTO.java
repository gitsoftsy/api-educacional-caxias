package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaTelefone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaTelefoneDTO {
	
	private String telefone;
	private Long idTelefoneEscola;
	
	private Long escolaId;
	
	private TipoTelefoneDTO tipoTelefone;
	
	private String descricao;
	
	
	
	public EscolaTelefoneDTO(EscolaTelefone escolaTelefone) {
		this.telefone = escolaTelefone.getTelefone();
		this.idTelefoneEscola = escolaTelefone.getIdTelefoneEscola();
		this.escolaId = escolaTelefone.getEscola().getIdEscola();
		this.tipoTelefone = new TipoTelefoneDTO(escolaTelefone.getTipoTelefone());
		this.descricao = escolaTelefone.getDescricao();
		
	}
}
