package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContaDTO {
	
	private Long idConta;
	
	@NotNull
	private String conta;
	
	@NotNull
	private String tipoConta;
	
	@NotNull
	@CNPJ
	private String cnpj;
	
	@NotNull
	private String cep;
	
	@NotNull
	private String endereco;
	@NotNull
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	@NotNull
	private String municipio;
	
	private String distrito;
	
	@NotNull
	private String uf;
	
	private LocalDateTime dataCadastro;
	
	private Character ativo;
	
	public ContaDTO(Conta conta) {
		this.idConta = conta.getIdConta();
		this.conta = conta.getConta();
		this.tipoConta = conta.getTipoConta();
		this.cnpj = conta.getCnpj();
		this.cep = conta.getCep();
		this.endereco = conta.getEndereco();
		this.numero = conta.getNumero();
		this.complemento = conta.getComplemento();
		this.bairro = conta.getBairro();
		this.municipio = conta.getMunicipio();
		this.distrito = conta.getDistrito();
		this.uf = conta.getUf();
		this.ativo = conta.getAtivo();
		this.dataCadastro = conta.getDataCadastro();
	}

}
