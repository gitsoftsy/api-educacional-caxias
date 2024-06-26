package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.softsy.educacional.model.Escola;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroEscolaDTO {
	private Long idEscola;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	@NotNull
	private String nomeEscola;
	
	private String logoEscola;
	
	@NotNull
	private String tipoEscola;
	
	
	private String cnpj;
	
	@NotNull
	private String codigoInep;
	
	@NotNull
	private String cep;
	
	@NotNull
	private String endereco;
	
	@NotNull
	private String numero;
	
	private String bairro;
	
	@NotNull
	private String municipio;
	
	@NotNull
	private String uf;
	
	private String numCME;
	
	private String numParecerCME;
	
	private String latitude;
	
	private String longitude;
	
	@NotNull
	private String email;
	
	@NotNull
	private Character educacaoIndigena;
	
	@NotNull 
	private Character exameSelecao;
	
	@NotNull
	private Character compartilhaEspaco;
	
	@NotNull
	private Character usaEspacoEntornoEscolar;
	
	@NotNull
	private Character pppAtualizado12Meses;
	
	private Long localizacaoId;
	
	private Long dependenciaAdmId;
	
	private Long situacaoFuncionamentoId;
	
	private Long formaOcupacaoPredioId;
	
	private Long zoneamentoId;
	
	private Long categoriaEscolaPrivadaId;
	
	private Long entidadeSuperiorId;

	private Long orgaoPublicoId;
	
	@NotNull
	private Long contaId;
	
	public CadastroEscolaDTO(Escola escola) {
		this.idEscola = escola.getIdEscola();
		this.dataCadastro = escola.getDataCadastro();
		this.ativo = escola.getAtivo();
		this.nomeEscola = escola.getNomeEscola();
		this.logoEscola = escola.getLogoEscola();
		this.tipoEscola = escola.getTipoEscola();
		this.cnpj = escola.getCnpj();
		this.codigoInep = escola.getCodigoInep();
		this.cep = escola.getCep();
		this.endereco = escola.getEndereco();
		this.bairro = escola.getBairro();
		this.numero = escola.getNumero();
		this.municipio = escola.getMunicipio();
		this.numCME = escola.getNumCME();
		this.uf = escola.getUf();
		this.numParecerCME = escola.getNumParecerCME();
		this.longitude = escola.getLongitude();
		this.latitude = escola.getLatitude();
		this.email = escola.getEmail();
		this.educacaoIndigena = escola.getEducacaoIndigena();
		this.exameSelecao = escola.getExameSelecao();
		this.compartilhaEspaco = escola.getCompartilhaEspaco();
		this.usaEspacoEntornoEscolar = escola.getUsaEspacoEntornoEscolar();
		this.pppAtualizado12Meses = escola.getPppAtualizado12Meses();
		this.localizacaoId = escola.getLocalizacao()!= null ? escola.getLocalizacao().getIdLocalizacao() : null;
		this.dependenciaAdmId = escola.getDependenciaAdm() != null ? escola.getDependenciaAdm().getIdDependenciaAdministrativa() : null;
		this.situacaoFuncionamentoId = escola.getSituacaoFuncionamento()!= null ? escola.getSituacaoFuncionamento().getIdSituacaoFuncionamento() : null;
		this.formaOcupacaoPredioId = escola.getFormaOcupacaoPredio() != null ? escola.getFormaOcupacaoPredio().getIdFormaOcupacaoPredio() : null;
		this.zoneamentoId = escola.getZoneamento() != null ? escola.getZoneamento().getIdZoneamento() : null;
		this.categoriaEscolaPrivadaId = escola.getCategoriaEscolaPrivada()!= null ? escola.getCategoriaEscolaPrivada().getIdCategoriaEscolaPrivada() : null;
		this.entidadeSuperiorId = escola.getEntidadeSuperior() != null ? escola.getEntidadeSuperior().getIdEntidadeSuperior() : null;
		this.orgaoPublicoId = escola.getOrgaoPublico() != null ? escola.getOrgaoPublico().getIdOrgaoPublico() : null;
		this.contaId = escola.getConta().getIdConta();
	}

}
