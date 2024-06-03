package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaTelefone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaTelefoneDTO {

	
private Long idTelefoneEscola;
	
	private Long escolaId;
	
	private Long tipoTelefoneId;
	
	private String telefone;
	
	private String descricao;
	
	public CadastroEscolaTelefoneDTO(EscolaTelefone escolaTelefone) {
		this.idTelefoneEscola = escolaTelefone.getIdTelefoneEscola();
		this.escolaId = escolaTelefone.getEscola().getIdEscola();
		this.tipoTelefoneId = escolaTelefone.getTipoTelefone().getIdTipoTelefone();
		this.telefone = escolaTelefone.getTelefone();
		this.descricao = escolaTelefone.getDescricao();
	}
}
