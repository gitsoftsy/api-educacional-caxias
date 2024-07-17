package br.com.softsy.educacional.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.softsy.educacional.model.PessoaFichaMedica;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaFichaMedicaDTO {

	private Long idPessoaFichaMedica;
	
	@NotNull
	private Long pessoaId;
	private LocalDateTime dtCadastro;
	private Long responsavelPessoaId;
	@NotNull
	private BigDecimal peso;
	@NotNull
	private BigDecimal altura;
	@NotNull
	private String tipoSanguineo;
	private Character aceitaTransfusao;
	private String numeroCartSus;
	private String planoSaude;
	private String numeroCarteirinha;
	private String psEmergenciaCep;
	private String psEmergenciaEndereco;
	private String psEmergenciaNumero;
	private String psEmergenciaComplemento;
	private String psEmergenciaBairro;
	private String psEmergenciaMunicipio;
	private String psEmergenciaDistrito;
	private String psEmergenciaUf;
	private String psEmergenciaTelefone;
	private Character alergia;
	private String descricaoAlergia;
	private Character tratamentoMedico;
	private String descricaoTratamentoMedico;
	private Character comorbidades;
	private String descricaoComorbidades;
	private String outrasDoencas;

	public PessoaFichaMedicaDTO(PessoaFichaMedica ficha) {
		this.idPessoaFichaMedica = ficha.getIdPessoaFichaMedica();
		this.pessoaId = ficha.getPessoa().getIdPessoa();
		this.dtCadastro = ficha.getDtCadastro();
		this.responsavelPessoaId = ficha.getResponsavelPessoa().getIdPessoa();
		this.peso = ficha.getPeso();
		this.altura = ficha.getAltura();
		this.tipoSanguineo = ficha.getTipoSanguineo();
		this.aceitaTransfusao = ficha.getAceitaTransfusao();
		this.numeroCartSus = ficha.getNumeroCartSus();
		this.planoSaude = ficha.getPlanoSaude();
		this.numeroCarteirinha = ficha.getNumeroCarteirinha();
		this.psEmergenciaCep = ficha.getPsEmergenciaCep();
		this.psEmergenciaEndereco = ficha.getPsEmergenciaEndereco();
		this.psEmergenciaNumero = ficha.getPsEmergenciaNumero();
		this.psEmergenciaComplemento = ficha.getPsEmergenciaComplemento();
		this.psEmergenciaBairro = ficha.getPsEmergenciaBairro();
		this.psEmergenciaMunicipio = ficha.getPsEmergenciaMunicipio();
		this.psEmergenciaDistrito = ficha.getPsEmergenciaDistrito();
		this.psEmergenciaUf = ficha.getPsEmergenciaUf();
		this.psEmergenciaTelefone = ficha.getPsEmergenciaTelefone();
		this.alergia = ficha.getAlergia();
		this.descricaoAlergia = ficha.getDescricaoAlergia();
		this.tratamentoMedico = ficha.getTratamentoMedico();
		this.descricaoTratamentoMedico = ficha.getDescricaoTratamentoMedico();
		this.comorbidades = ficha.getComorbidades();
		this.descricaoComorbidades = ficha.getDescricaoComorbidades();
		this.outrasDoencas = ficha.getOutrasDoencas();
	}
}