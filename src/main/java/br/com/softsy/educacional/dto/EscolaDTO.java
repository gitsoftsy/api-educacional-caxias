package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.FormaOcupacaoPredio;
import br.com.softsy.educacional.model.Localizacao;
import br.com.softsy.educacional.model.SituacaoFuncionamento;
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
	private String logoEscola;
	
	@NotNull
	private String tipoEscola;
	
	@NotNull
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
	private LocalizacaoDTO localizacao;
	
	@NotNull
	private DependenciaAdministrativaDTO dependenciaAdm;
	
	@NotNull
	private SituacaoFuncionamentoDTO situacaoFuncionamento;
	
	@NotNull
	private FormaOcupacaoPredioDTO formaOcupacaoPredio;
	
	
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
		this.acessivel = escola.getAcessivel();
		
		this.localizacao = new LocalizacaoDTO(escola.getLocalizacao());
//	    this.localizacao.setIdLocalizacao(escola.getLocalizacao().getIdLocalizacao());
//	    this.localizacao.setLocalizacao(escola.getLocalizacao().getLocalizacao());
	    
		this.dependenciaAdm = new DependenciaAdministrativaDTO(escola.getDependenciaAdm());
//		this.dependenciaAdm.setIdDependenciaAdministrativa(escola.getDependenciaAdm().getIdDependenciaAdministrativa());
//		this.dependenciaAdm.setDependenciaAdministrativa(escola.getDependenciaAdm().getDependenciaAdministrativa());
		
		this.situacaoFuncionamento = new SituacaoFuncionamentoDTO(escola.getSituacaoFuncionamento());
//		this.situacaoFuncionamento.setIdSituacaoFuncionamento(escola.getSituacaoFuncionamento().getIdSituacaoFuncionamento());
//		this.situacaoFuncionamento.setSituacaoFuncionamento(escola.getSituacaoFuncionamento().getSituacaoFuncionamento());
		
		
		this.formaOcupacaoPredio = new FormaOcupacaoPredioDTO(escola.getFormaOcupacaoPredio());
//		this.formaOcupacaoPredio.setIdFormaOcupacaoPredio(escola.getFormaOcupacaoPredio().getIdFormaOcupacaoPredio());
//		this.formaOcupacaoPredio.setFormaOcupacaoPredio(escola.getFormaOcupacaoPredio().getFormaOcupacaoPredio());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
