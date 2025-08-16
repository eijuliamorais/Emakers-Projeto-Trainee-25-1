package com.example.springboot.exception;

public class LivroJaEmprestadoException extends RuntimeException {
    public LivroJaEmprestadoException(String msg){
        super(msg);
    }
}
