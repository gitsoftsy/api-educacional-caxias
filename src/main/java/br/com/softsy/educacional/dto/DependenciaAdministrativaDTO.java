package br.com.softsy.educacional.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.softsy.educacional.model.DependenciaAdministrativa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DependenciaAdministrativaDTO {

	private Long idDependenciaAdministrativa;
	
	@NotNull
	private String dependenciaAdministrativa;
	
	@NotNull
	private String tipoDependencia;
	
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
	
	public DependenciaAdministrativaDTO(DependenciaAdministrativa dependenciaAdm) {
		this.idDependenciaAdministrativa = dependenciaAdm.getIdDependenciaAdministrativa();
		this.dependenciaAdministrativa = dependenciaAdm.getDependenciaAdministrativa();
		this.tipoDependencia = dependenciaAdm.getTipoDependencia();
		this.cnpj = dependenciaAdm.getCnpj();
		this.cep = dependenciaAdm.getCep();
		this.endereco = dependenciaAdm.getEndereco();
		this.numero = dependenciaAdm.getNumero();
		this.complemento = dependenciaAdm.getComplemento();
		this.bairro = dependenciaAdm.getBairro();
		this.municipio = dependenciaAdm.getMunicipio();
		this.distrito = dependenciaAdm.getDistrito();
		this.uf = dependenciaAdm.getUf();
		this.ativo = dependenciaAdm.getAtivo();
		this.dataCadastro = dependenciaAdm.getDataCadastro();
	}
}
