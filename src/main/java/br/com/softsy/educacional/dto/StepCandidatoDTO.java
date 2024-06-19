package br.com.softsy.educacional.dto;

public class StepCandidatoDTO {
    private Long idCandidato;
    private String candidato;
    private String step;
    private String temRelacionamento;
    private String temOfertaConcurso;
    private String enviouDocumentos;


    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getCandidato() {
        return candidato;
    }

    public void setCandidato(String candidato) {
        this.candidato = candidato;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getTemRelacionamento() {
        return temRelacionamento;
    }

    public void setTemRelacionamento(String temRelacionamento) {
        this.temRelacionamento = temRelacionamento;
    }

    public String getTemOfertaConcurso() {
        return temOfertaConcurso;
    }

    public void setTemOfertaConcurso(String temOfertaConcurso) {
        this.temOfertaConcurso = temOfertaConcurso;
    }

    public String getEnviouDocumentos() {
        return enviouDocumentos;
    }

    public void setEnviouDocumentos(String enviouDocumentos) {
        this.enviouDocumentos = enviouDocumentos;
    }
}