package br.com.softsy.educacional.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TBL_AGENDA_ANEXO")
@Data
public class AgendaAnexo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_AGENDA_ANEXO")
	private Long idAgendaAnexo;
	
	@ManyToOne
	@JoinColumn(name = "ID_AGENDA", nullable = false)
	private Agenda agenda;
	
	@Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name = "CAMINHO_ARQUIVO", length = 14)
	private String caminhoArquivo;
	
	@Column(name = "ATIVO", nullable = false)
	private Character ativo;

}
