package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.ProvedorInternet;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProvedorInternetDTO {

    private Long idProvedorInternet;
    
	@NotNull
	private Long dependenciaAdmId;

    @NotNull
    private String provedorInternet;
    private String telefoneProvedor;
    private LocalDateTime dataCadastro;
    private Character ativo;

    public ProvedorInternetDTO(ProvedorInternet provedorInternet) {
        this.provedorInternet = provedorInternet.getProvedorInternet();
        this.idProvedorInternet = provedorInternet.getIdProvedorInternet();
        this.telefoneProvedor = provedorInternet.getTelefoneProvedor();
        this.dataCadastro = provedorInternet.getDataCadastro();
        this.ativo = provedorInternet.getAtivo();
        this.dependenciaAdmId = provedorInternet.getDependenciaAdm().getIdDependenciaAdministrativa();
    }
}