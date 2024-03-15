package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaRegimeEscolar;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaRegimeEscolarDTO {
	
	private Long idEscolaRegimeEscolar;
	
	@NotNull
    private Long escolaId;
    
    @NotNull
    private String descricao;
    
    @NotNull
    private LocalDateTime dataHomologacao;
    
    @NotNull
    private LocalDateTime dataInicioVigencia;
    
    @NotNull
    private LocalDateTime dataFimVigencia;
    
    @NotNull
    private Integer anoCiclo;
    

    private PeriodicidadeDTO periodicidade;
    
    private String anexo;
    
    public EscolaRegimeEscolarDTO(EscolaRegimeEscolar regimeEscolar) {
    	this.idEscolaRegimeEscolar = regimeEscolar.getIdEscolaRegimeEscolar();
    	this.escolaId = regimeEscolar.getEscola().getIdEscola();
    	this.descricao = regimeEscolar.getDescricao();
    	this.dataHomologacao = regimeEscolar.getDataHomologacao();
    	this.dataInicioVigencia = regimeEscolar.getDataInicioVigencia();
    	this.dataFimVigencia = regimeEscolar.getDataFimVigencia();
    	this.anoCiclo = regimeEscolar.getAnoCiclo();
    	this.periodicidade = new PeriodicidadeDTO(regimeEscolar.getPeriodicidade());
    	this.anexo = regimeEscolar.getAnexo();
    }
	
}
