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
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "TBL_MOTIVO_REPROVACAO_CANDIDATO", 
	uniqueConstraints = { 
		@UniqueConstraint(name = "UQ_MOTIVO_REPROVACAO_CANDIDATO", columnNames = { "MOTIVO_REPROVACAO_CANDIDATO", "ID_CONTA" })
		})
@Data
public class MotivoReprovacaoCandidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOTIVO_REPROVACAO_CANDIDATO")
    private Long idMotivoReprovacaoCandidato;

    @ManyToOne
    @JoinColumn(name = "ID_CONTA", nullable = false)
    private Conta conta;

    @Column(name = "MOTIVO_REPROVACAO_CANDIDATO", nullable = false, length = 255)
    private String motivoReprovacaoCandidato;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "ATIVO", nullable = false)
    private Character ativo;

    @Column(name = "OBRIGATORIO", nullable = false)
    private Character obrigatorio;
}