package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;
import org.apache.commons.codec.binary.Base64;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.softsy.educacional.model.Escola;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscolaDTO {

	private Long idEscola;

	private LocalDateTime dataCadastro;
	private Character ativo;

	@NotNull
	private String nomeEscola;

	@NotNull
	private ContaDTO conta;

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

	private LocalizacaoDTO localizacao;

	private DependenciaAdministrativaDTO dependenciaAdm;

	private SituacaoFuncionamentoDTO situacaoFuncionamento;

	private FormaOcupacaoPredioDTO formaOcupacaoPredio;

	private ZoneamentoDTO zoneamento;

	private EntidadeSuperiorDTO entidadeSuperior;

	private CategoriaEscolaPrivadaDTO categoriaEscolaPrivada;

	private OrgaoPublicoDTO orgaoPublico;

	public EscolaDTO(Escola escola) {
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

		if (escola.getLocalizacao() != null) {
			this.localizacao = new LocalizacaoDTO(escola.getLocalizacao());
		} else {
			this.localizacao = null;
		}

		if (escola.getDependenciaAdm() != null) {
			this.dependenciaAdm = new DependenciaAdministrativaDTO(escola.getDependenciaAdm());
		} else {
			this.dependenciaAdm = null;
		}

		if (escola.getSituacaoFuncionamento() != null) {
			this.situacaoFuncionamento = new SituacaoFuncionamentoDTO(escola.getSituacaoFuncionamento());
		} else {
			this.situacaoFuncionamento = null;
		}

		if (escola.getFormaOcupacaoPredio() != null) {
			this.formaOcupacaoPredio = new FormaOcupacaoPredioDTO(escola.getFormaOcupacaoPredio());
		} else {
			this.formaOcupacaoPredio = null;
		}

		if (escola.getZoneamento() != null) {
			this.zoneamento = new ZoneamentoDTO(escola.getZoneamento());
		} else {
			this.zoneamento = null;
		}

		if (escola.getCategoriaEscolaPrivada() != null) {
			this.categoriaEscolaPrivada = new CategoriaEscolaPrivadaDTO(escola.getCategoriaEscolaPrivada());
		} else {
			this.categoriaEscolaPrivada = null;
		}

		if (escola.getEntidadeSuperior() != null) {
			this.entidadeSuperior = new EntidadeSuperiorDTO(escola.getEntidadeSuperior());
		} else {
			this.entidadeSuperior = null;
		}

		if (escola.getOrgaoPublico() != null) {
			this.orgaoPublico = new OrgaoPublicoDTO(escola.getOrgaoPublico());
		} else {
			this.orgaoPublico = null;
		}

		this.conta = new ContaDTO(escola.getConta());
	}

}
