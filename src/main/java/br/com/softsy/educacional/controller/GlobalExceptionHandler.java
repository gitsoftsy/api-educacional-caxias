package br.com.softsy.educacional.controller;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.UniqueKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.softsy.educacional.model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private EntityManager entityManager;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            org.hibernate.exception.ConstraintViolationException constraintEx =
                    (org.hibernate.exception.ConstraintViolationException) e.getCause();

            String constraintName = constraintEx.getConstraintName();
            String tableName = extractTableName(constraintEx.getSQLException().getMessage());

            List<String> columns = getColumnsForConstraint(tableName, constraintName);

            String message = String.format(
                    "Violação de chave única detectada. Restrição: %s.",
                    constraintName,
                    String.join(", ", columns)
            );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro de violação de chave única", message));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro interno", e.getMessage()));
    }

    public List<String> getColumnsForConstraint(String tableName, String constraintName) {
        try {
            SessionFactory sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
            MetadataImplementor metadata = (MetadataImplementor) sessionFactory.getMetamodel();

            for (PersistentClass persistentClass : metadata.getEntityBindings()) {
                Table table = persistentClass.getTable();
                if (table != null && table.getName().equalsIgnoreCase(tableName)) {
                    UniqueKey uniqueKey = table.getUniqueKey(constraintName);
                    if (uniqueKey != null) {
                        return uniqueKey.getColumns().stream()
                                .map(Column::getName)
                                .collect(Collectors.toList());
                    }
                }
            }
        } catch (Exception e) {
            // Loga o erro para ajudar na depuração
            System.err.println("Erro ao buscar as colunas da restrição: " + e.getMessage());
            e.printStackTrace();
        }
        return Collections.emptyList(); // Retorna uma lista vazia em caso de erro
    }
    
    public String extractTableName(String sqlMessage) {
        if (sqlMessage == null || sqlMessage.isEmpty()) {
            return "Não identificado";
        }

        try {
            String regex = "(?i)table\\s*`?(\\w+)`?";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(sqlMessage);

            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception e) {
            // Loga o erro para debug
            System.err.println("Erro ao extrair nome da tabela: " + e.getMessage());
            e.printStackTrace();
        }

        return "Não identificado"; // Nome da tabela não identificado
    }
    
}