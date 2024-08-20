package br.com.softsy.educacional.model;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "TBL_AGENDA")
@Data
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDA")
    private Long idAgenda;

    @ManyToOne
    @JoinColumn(name = "ID_TURMA", nullable = false)
    private Turma turma;

    @Column(name = "DT_AGENDA", nullable = false)
    private LocalDate dataAgenda;

    @Column(name = "HORA_INICIO", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "HORA_FIM", nullable = false)
    private LocalTime horaFim;

    @Column(name = "REALIZADA", nullable = false)
    private Character realizada;

    @Column(name = "TITULO_AULA", length = 255)
    private String tituloAula;

    @Column(name = "RESUMO_AULA", length = 2500)
    private String resumoAula;

    @Column(name = "ATIVO", nullable = false)
    private Character ativo;

}