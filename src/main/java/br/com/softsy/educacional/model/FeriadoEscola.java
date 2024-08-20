package br.com.softsy.educacional.model;

import java.time.LocalDate;
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
@Table(name = "TBL_FERIADO_ESCOLA")
@Data
public class FeriadoEscola {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FERIADO_ESCOLA")
    private Long idFeriadoEscola;

    @Column(name = "DESCRICAO", length = 255)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "ID_ESCOLA", nullable = false)
    private Escola escola;

    @Column(name = "DT_FERIADO", nullable = false)
    private LocalDate dataFeriado;

    @Column(name = "DT_CADASTRO", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "ATIVO", nullable = false)
    private Character ativo;

}
