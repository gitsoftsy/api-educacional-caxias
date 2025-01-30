package br.com.softsy.educacional.dto;

public class EditalDTO {
    private Long idConcurso;
    private String nomeEdital;
    private String pathEdital;


    public EditalDTO(Long idConcurso, String concurso, String pathEdital) {
        this.idConcurso = idConcurso;
        this.nomeEdital = "Edital " + concurso;
        this.pathEdital = pathEdital;
    }

    public Long getIdConcurso() {
        return idConcurso;
    }

    public void setIdConcurso(Long idConcurso) {
        this.idConcurso = idConcurso;
    }

    public String getNomeEdital() {
        return nomeEdital;
    }

    public void setNomeEdital(String nomeEdital) {
        this.nomeEdital = nomeEdital;
    }

    public String getPathEdital() {
        return pathEdital;
    }

    public void setPathEdital(String pathEdital) {
        this.pathEdital = pathEdital;
    }
}
