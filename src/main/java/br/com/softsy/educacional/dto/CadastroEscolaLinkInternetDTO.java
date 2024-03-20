package br.com.softsy.educacional.dto;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.EscolaLinkInternet;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaLinkInternetDTO {

    private Long idEscolaLinkInternet;
    @NotNull
    private Long escolaId;
    @NotNull
    private Long provedorInternetId;
    private Double velocidadeMb;
    private Character administrativo;
    private Character estudante;
    private Character ativo;

    public CadastroEscolaLinkInternetDTO(EscolaLinkInternet escolaLinkInternet) {
        this.idEscolaLinkInternet = escolaLinkInternet.getIdEscolaLinkInternet();
        this.escolaId = escolaLinkInternet.getEscola().getIdEscola();
        this.provedorInternetId = escolaLinkInternet.getProvedorInternet().getIdProvedorInternet();
        this.velocidadeMb = escolaLinkInternet.getVelocidadeMb();
        this.administrativo = escolaLinkInternet.getAdministrativo();
        this.estudante = escolaLinkInternet.getEstudante();
        this.ativo = escolaLinkInternet.getAtivo();
    }
}