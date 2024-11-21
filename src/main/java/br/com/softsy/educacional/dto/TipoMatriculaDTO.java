package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.TipoMatricula;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoMatriculaDTO {
	
	 private Long idTipoMatricula;
	 @NotNull
	 private Long contaId;
	 @NotNull
	 private String tipoMatricula;
	 
	 public TipoMatriculaDTO(TipoMatricula tipoMatricula) {
		 
		 this.idTipoMatricula = tipoMatricula.getIdTipoMatricula();
		 this.contaId = tipoMatricula.getConta().getIdConta();
		 this.tipoMatricula = tipoMatricula.getTipoMatricula();
		 
	 }

}
