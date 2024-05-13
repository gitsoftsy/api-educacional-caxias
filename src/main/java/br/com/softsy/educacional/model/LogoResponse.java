package br.com.softsy.educacional.model;

import org.apache.commons.codec.binary.Base64;

public class LogoResponse {

    private String logoEscola;

    public LogoResponse(byte[] logo) {
        this.logoEscola = Base64.encodeBase64String(logo);
    }

    public String getlogoEscola() {
        return logoEscola;
    }

    public void setlogoEscola(String logoEscola) {
        this.logoEscola = logoEscola;
    }
}