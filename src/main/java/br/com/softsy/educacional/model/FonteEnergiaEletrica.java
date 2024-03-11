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
@Table(name = "TBL_FONTE_ENERGIA_ELETRICA")
@Data
public class FonteEnergiaEletrica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_FONTE_ENERGIA_ELETRICA")
	private Long idFonteEnergiaEletrica;
	
	@Column(name = "FONTE_ENERGIA_ELETRICA", nullable = false, unique = true)
	private String fonteEnergiaEletrica;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;
}
