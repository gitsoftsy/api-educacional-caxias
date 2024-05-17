package br.com.softsy.educacional.model;

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
@Table(name = "TBL_ESCOLA_INFRAESTRUTURA")
@Data
public class EscolaInfraestrutura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESCOLA_INFRAESTRUTURA")
    private Long idEscolaInfraestrutura;

    @ManyToOne
    @JoinColumn(name = "ID_ESCOLA", nullable = false)
    private Escola escola;

    @Column(name = "ESCOLA_ACESSIVEL", nullable = false, length = 1)
    private Character escolaAcessivel;

    @Column(name = "DEPENDENCIAS_ACESSIVEIS", nullable = false, length = 1)
    private Character dependenciasAcessiveis;

    @Column(name = "SANITARIOS_ACESSIVEIS", nullable = false, length = 1)
    private Character sanitariosAcessiveis;

    @Column(name = "ALIMENTACAO_FORNECIDA", nullable = false, length = 1)
    private Character alimentacaoFornecida;

    @Column(name = "AGUA_FILTRADA", nullable = false, length = 1)
    private Character aguaFiltrada;

    @Column(name = "SANITARIO_DENTRO_ESCOLA", nullable = false, length = 1)
    private Character sanitarioDentroEscola;

    @Column(name = "BIBLIOTECA", nullable = false, length = 1)
    private Character biblioteca;

    @Column(name = "COZINHA", nullable = false, length = 1)
    private Character cozinha;

    @Column(name = "LAB_INFORMATICA", nullable = false, length = 1)
    private Character labInformatica;

    @Column(name = "LAB_CIENCIAS", nullable = false, length = 1)
    private Character labCiencias;

    @Column(name = "SALA_LEITURA", nullable = false, length = 1)
    private Character salaLeitura;

    @Column(name = "QUADRA_ESPORTES", nullable = false, length = 1)
    private Character quadraEsportes;

    @Column(name = "SALA_DIRETORIA", nullable = false, length = 1)
    private Character salaDiretoria;

    @Column(name = "SALA_PROFESSORES", nullable = false, length = 1)
    private Character salaProfessores;

    @Column(name = "SALA_ATENDIMENTO_ESPECIAL", nullable = false, length = 1)
    private Character salaAtendimentoEspecial;

    @Column(name = "INTERNET", nullable = false, length = 1)
    private Character internet;

    @Column(name = "BANDA_LARGA", nullable = false, length = 1)
    private Character bandaLarga;
}