package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaInfraestrutura;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaInfraestruturaDTO {

    private Long idEscolaInfraestrutura;
    
    @NotNull
    private Long escolaId;
    
    @NotNull
    private Character escolaAcessivel;
    
    @NotNull
    private Character dependenciasAcessiveis;
    
    @NotNull
    private Character sanitariosAcessiveis;
    
    @NotNull
    private Character alimentacaoFornecida;
    
    @NotNull
    private Character aguaFiltrada;
    
    @NotNull
    private Character sanitarioDentroEscola;
    
    @NotNull
    private Character biblioteca;
    
    @NotNull
    private Character cozinha;
    
    @NotNull
    private Character labInformatica;
    
    @NotNull
    private Character labCiencias;
    
    @NotNull
    private Character salaLeitura;
    
    @NotNull
    private Character quadraEsportes;
    
    @NotNull
    private Character salaDiretoria;
    
    @NotNull
    private Character salaProfessores;
    
    @NotNull
    private Character salaAtendimentoEspecial;
    
    @NotNull
    private Character internet;
    
    @NotNull
    private Character bandaLarga;

    public EscolaInfraestruturaDTO(EscolaInfraestrutura escolaInfraestrutura) {
        this.idEscolaInfraestrutura = escolaInfraestrutura.getIdEscolaInfraestrutura();
        this.escolaId = escolaInfraestrutura.getEscola().getIdEscola();
        this.escolaAcessivel = escolaInfraestrutura.getEscolaAcessivel();
        this.dependenciasAcessiveis = escolaInfraestrutura.getDependenciasAcessiveis();
        this.sanitariosAcessiveis = escolaInfraestrutura.getSanitariosAcessiveis();
        this.alimentacaoFornecida = escolaInfraestrutura.getAlimentacaoFornecida();
        this.aguaFiltrada = escolaInfraestrutura.getAguaFiltrada();
        this.sanitarioDentroEscola = escolaInfraestrutura.getSanitarioDentroEscola();
        this.biblioteca = escolaInfraestrutura.getBiblioteca();
        this.cozinha = escolaInfraestrutura.getCozinha();
        this.labInformatica = escolaInfraestrutura.getLabInformatica();
        this.labCiencias = escolaInfraestrutura.getLabCiencias();
        this.salaLeitura = escolaInfraestrutura.getSalaLeitura();
        this.quadraEsportes = escolaInfraestrutura.getQuadraEsportes();
        this.salaDiretoria = escolaInfraestrutura.getSalaDiretoria();
        this.salaProfessores = escolaInfraestrutura.getSalaProfessores();
        this.salaAtendimentoEspecial = escolaInfraestrutura.getSalaAtendimentoEspecial();
        this.internet = escolaInfraestrutura.getInternet();
        this.bandaLarga = escolaInfraestrutura.getBandaLarga();
    }
}