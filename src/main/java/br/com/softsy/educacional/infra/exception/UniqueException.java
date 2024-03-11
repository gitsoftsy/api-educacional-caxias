package br.com.softsy.educacional.infra.exception;

public class UniqueException extends RuntimeException {
    public UniqueException(String mensagem) {
        super(mensagem);
    }
}
