package br.com.softsy.educacional.dto;

import br.com.softsy.educacional.model.EscolaLinkInternet;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaLinkInternetDTO {

    private Long idEscolaLinkInternet;
    private Long escolaId;
    private ProvedorInternetDTO provedorInternet;
    private Double velocidadeMb;
    private Character administrativo;
    private Character estudante;
    private Character ativo;

    public EscolaLinkInternetDTO(EscolaLinkInternet escolaLinkInternet) {
        this.idEscolaLinkInternet = escolaLinkInternet.getIdEscolaLinkInternet();
        this.escolaId = escolaLinkInternet.getEscola().getIdEscola();
        this.provedorInternet = new ProvedorInternetDTO(escolaLinkInternet.getProvedorInternet());
        this.velocidadeMb = escolaLinkInternet.getVelocidadeMb();
        this.administrativo = escolaLinkInternet.getAdministrativo();
        this.estudante = escolaLinkInternet.getEstudante();
        this.ativo = escolaLinkInternet.getAtivo();
    }
}
