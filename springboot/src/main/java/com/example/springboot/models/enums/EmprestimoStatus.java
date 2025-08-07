package com.example.springboot.models.enums;

//declarar se o livro esta emprestado ou ja foi devolvido
public enum EmprestimoStatus {
    EMPRESTADO("Emprestado"),
    DEVOLVIDO("Devolvido"),
    ATRASADO("Atrasado");

    private final String status;

    private EmprestimoStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
