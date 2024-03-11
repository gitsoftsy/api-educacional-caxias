package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_FORMA_OCUPACAO_PREDIO")
@Data
public class FormaOcupacaoPredio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_FORMA_OCUPACAO_PREDIO")
	private Long idFormaOcupacaoPredio;
	
	@Column(name = "FORMA_OCUPACAO_PREDIO", nullable = false, unique = true)
	private String formaOcupacaoPredio;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
