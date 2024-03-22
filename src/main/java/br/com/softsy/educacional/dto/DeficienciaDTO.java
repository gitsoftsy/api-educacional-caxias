package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import br.com.softsy.educacional.model.Deficiencia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeficienciaDTO {

	private Long idDeficiencia;

	private String deficiencia;

	private String cid;

	private LocalDateTime dataCadastro;

	private Character ativo;

	public DeficienciaDTO(Deficiencia deficiencia) {
		this.idDeficiencia = deficiencia.getIdDeficiencia();
		this.deficiencia = deficiencia.getDeficiencia();
		this.cid = deficiencia.getCid();
		this.dataCadastro = deficiencia.getDataCadastro();
		this.ativo = deficiencia.getAtivo();
	}
}
