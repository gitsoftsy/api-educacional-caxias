package br.com.softsy.educacional.model;

import br.com.softsy.educacional.infra.config.PasswordEncrypt;

public class TesteCriptografia {
    public static void main(String[] args) {
        PasswordEncrypt encrypt = new PasswordEncrypt();
        encrypt.executarCriptografia();
    }
}
