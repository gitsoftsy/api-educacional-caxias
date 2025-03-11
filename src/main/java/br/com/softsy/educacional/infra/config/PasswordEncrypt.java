package br.com.softsy.educacional.infra.config;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncrypt {
    public String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    
    public void executarCriptografia() {
        String senha = "#@Sum@re#@";
        String senhaCriptografada = hashPassword(senha);
        System.out.println("Senha original: " + senha);
        System.out.println("Senha criptografada: " + senhaCriptografada);
    }
}
