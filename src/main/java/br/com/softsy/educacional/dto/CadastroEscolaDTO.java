package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;
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
	
	@NotNull
	private String logoEscola;
	
	@NotNull
	private String tipoEscola;
	
	@CNPJ
	private String cnpj;
	
	private String codigoInep;
	
	@NotNull
	private String cep;
	
	@NotNull
	private String endereco;
	
	@NotNull
	private String numero;
	
	@NotNull
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
	
	@NotNull
	private Character acessivel;
	
	@NotNull
	private Character merendaEscolar;
	
	@NotNull
	private Character possuiAguaPotavel;
	
	@NotNull
	private Character internetBandaLarga;
	
	@NotNull
	private Long localizacaoId;
	
	@NotNull
	private Long dependenciaAdmId;
	
	@NotNull
	private Long situacaoFuncionamentoId;
	
	@NotNull
	private Long formaOcupacaoPredioId;
	
	@NotNull
	private Long zoneamentoId;
	
	@NotNull
	private Long categoriaEscolaPrivadaId;
	
	@NotNull
	private Long entidadeSuperiorId;
	
	@NotNull
	private Long orgaoPublicoId;
	
	@NotNull
	private Long contaId;
	
	public CadastroEscolaDTO(Escola escola) {
		this.idEscola = escola.getIdEscola();
		this.dataCadastro = escola.getDataCadastro();
		this.ativo = escola.getAtivo();
		this.nomeEscola = escola.getNomeEscola();
		this.logoEscola = Base64.encodeBase64String(escola.getLogoEscola());
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
		this.acessivel = escola.getAcessivel();
		this.merendaEscolar = escola.getMerendaEscolar();
		this.internetBandaLarga = escola.getInternetBandaLarga();
		this.possuiAguaPotavel = escola.getPossuiAguaPotavel();
		this.localizacaoId = escola.getLocalizacao().getIdLocalizacao();
		this.dependenciaAdmId = escola.getDependenciaAdm().getIdDependenciaAdministrativa();
		this.situacaoFuncionamentoId = escola.getSituacaoFuncionamento().getIdSituacaoFuncionamento();
		this.formaOcupacaoPredioId = escola.getFormaOcupacaoPredio().getIdFormaOcupacaoPredio();
		this.zoneamentoId = escola.getZoneamento().getIdZoneamento();
		this.categoriaEscolaPrivadaId = escola.getCategoriaEscolaPrivada().getIdCategoriaEscolaPrivada();
		this.entidadeSuperiorId = escola.getEntidadeSuperior().getIdEntidadeSuperior();
		this.orgaoPublicoId = escola.getOrgaoPublico().getIdOrgaoPublico();
		this.contaId = escola.getConta().getIdConta();
	}

}
