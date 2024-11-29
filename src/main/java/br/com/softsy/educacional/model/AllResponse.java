package br.com.softsy.educacional.model;

import java.util.List;

public class AllResponse {
    private String message;
    private List<Object> data;

    public AllResponse(String message, List<Object> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}